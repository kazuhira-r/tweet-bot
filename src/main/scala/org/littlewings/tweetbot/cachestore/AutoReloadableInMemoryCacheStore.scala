package org.littlewings.tweetbot.cachestore

import java.util.concurrent.Executor

import org.infinispan.Cache
import org.infinispan.container.versioning.NumericVersion
import org.infinispan.filter.KeyFilter
import org.infinispan.marshall.core.{MarshalledEntry, MarshalledEntryFactory}
import org.infinispan.metadata.impl.InternalMetadataImpl
import org.infinispan.metadata.{EmbeddedMetadata, InternalMetadata}
import org.infinispan.persistence.spi.AdvancedCacheLoader.CacheLoaderTask
import org.infinispan.persistence.spi.AdvancedCacheWriter.PurgeListener
import org.infinispan.persistence.spi.{AdvancedLoadWriteStore, InitializationContext}
import org.infinispan.persistence.{PersistenceUtil, TaskContextImpl}
import org.littlewings.tweetbot.LoggerSupport

trait AutoReloadableInMemoryCacheStore[K, V] extends AdvancedLoadWriteStore[K, V] with LoggerSupport {
  protected val internalStore: scala.collection.mutable.Map[K, (V, InternalMetadata)] = scala.collection.mutable.Map.empty

  protected var cache: Cache[K, V] = _

  protected var context: InitializationContext = _

  protected def reload: Map[K, V]

  override def init(ctx: InitializationContext): Unit = {
    context = ctx
    cache = context.getCache.asInstanceOf[Cache[K, V]]
  }

  override def load(key: AnyRef): MarshalledEntry[K, V] = {
    logger.debug("Store Cache[{}] load key = {}", Array(cache.getName, key): _*)

    if (internalStore.isEmpty) {
      triggerReloadCacheStore()
    }

    val marshalledEntryFactory =
      context.getMarshalledEntryFactory.asInstanceOf[MarshalledEntryFactory[K, V]]

    val metadata = new EmbeddedMetadata.Builder().version(new NumericVersion(1L)).build
    val now = System.currentTimeMillis
    val (created, lastUsed) = (now, now)

    internalStore
      .get(key.asInstanceOf[K])
      .map { valueAndMetadata =>
        val (value, metadata) = valueAndMetadata
        marshalledEntryFactory.newMarshalledEntry(key, value, metadata)
      }
      .orNull
  }

  override def contains(key: AnyRef): Boolean = internalStore.get(key.asInstanceOf[K]).isDefined

  override def delete(key: scala.Any): Boolean = {
    logger.debug("Store Cache[{}] delete key = {}", cache.getName, key)

    val result = internalStore.remove(key.asInstanceOf[K]).isDefined

    if (internalStore.isEmpty) {
      triggerReloadCacheStore()
    }

    result
  }

  protected def triggerReloadCacheStore(): Unit = {
    val reloadedEntries = reload

    val marshalledEntryFactory =
      context.getMarshalledEntryFactory.asInstanceOf[MarshalledEntryFactory[K, V]]

    val now = System.currentTimeMillis
    val (created, lastUsed) = (now, now)

    reloadedEntries.foreach { case (key, value) =>
      val metadata = new EmbeddedMetadata.Builder().version(new NumericVersion(1L)).build
      internalStore += key -> (value -> new InternalMetadataImpl(metadata, created, lastUsed))
    }

    logger.info("Store Cache[{}] reloaded, size = {}", cache.getName, internalStore.size)
  }

  override def write(entry: MarshalledEntry[_ <: K, _ <: V]): Unit = {
    logger.debug("Store Cache[{}] write entry, key = {}", cache.getName, entry.getKey)
    internalStore += entry.getKey -> (entry.getValue -> entry.getMetadata)
  }

  override def clear(): Unit = {
    logger.debug("Store Cache[{}] clear", cache.getName)
    internalStore.clear()
  }

  override def purge(threadPool: Executor, listener: PurgeListener[_ >: K]): Unit = {
    logger.debug("Store Cache[{}] purge (ignored)", cache.getName)
    () // ignore
  }

  override def size(): Int = internalStore.size

  override def process(filter: KeyFilter[_ >: K], task: CacheLoaderTask[K, V], executor: Executor, fetchValue: Boolean, fetchMetadata: Boolean): Unit = {
    logger.debug("Store Cache[{}] process", cache.getName)

    val filterOrLoadAll = PersistenceUtil.notNull(filter).asInstanceOf[KeyFilter[K]]
    val taskContext = new TaskContextImpl

    try {
      internalStore
        .keys
        .filter(filterOrLoadAll.accept)
        .foreach { k =>
          if (!taskContext.isStopped) {
            Option(load(k.asInstanceOf[AnyRef])).foreach { e =>
              task.processEntry(e, taskContext)
            }
          }
        }
    } catch {
      case _: InterruptedException => Thread.currentThread.interrupt()
    }
  }

  override def stop(): Unit = {
    logger.info("Store Cache[{}] stop", cache.getName)
  }

  override def start(): Unit = {
    logger.info("Store Cache[{}] start", cache.getName)
    triggerReloadCacheStore()
  }
}
