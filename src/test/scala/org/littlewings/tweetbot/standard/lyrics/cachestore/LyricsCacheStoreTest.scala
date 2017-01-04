package org.littlewings.tweetbot.standard.lyrics.cachestore

import java.util.stream.Collectors

import org.infinispan.configuration.cache.ConfigurationBuilder
import org.infinispan.configuration.global.GlobalConfigurationBuilder
import org.infinispan.manager.{DefaultCacheManager, EmbeddedCacheManager}
import org.junit.Test
import org.littlewings.tweetbot.resource.Managed
import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.test.ScalaTestJUnitTestSupport

class LyricsCacheStoreTest extends ScalaTestJUnitTestSupport {
  private[cachestore] def withCacheManager(fun: EmbeddedCacheManager => Unit): Unit = {
    val configurationBuilder = new GlobalConfigurationBuilder
    configurationBuilder
      .globalJmxStatistics()
      .allowDuplicateDomains(true)

    val cacheManager = new DefaultCacheManager(configurationBuilder.build)

    try {
      fun(cacheManager)
    } finally {
      cacheManager.stop()
    }
  }

  @Test
  def cacheStoreConfiguration(): Unit =
    withCacheManager { cacheManager =>
      val cacheConfiguration = new ConfigurationBuilder
      cacheConfiguration
        .persistence
        .addStore(classOf[LyricsCacheStoreConfigurationBuilder])
        .addProperty("artistNameAlias", "singer")

      cacheManager.defineConfiguration("singerLyricsCache", cacheConfiguration.build)

      val cache = cacheManager.getCache[String, Lyrics]("singerLyricsCache")

      cache should have size 8
    }

  @Test
  def reloadCacheStore(): Unit =
    withCacheManager { cacheManager =>
      val cacheConfiguration = new ConfigurationBuilder
      cacheConfiguration
        .persistence
        .addStore(classOf[LyricsCacheStoreConfigurationBuilder])
        .addProperty("artistNameAlias", "singer")

      cacheManager.defineConfiguration("singerLyricsCache", cacheConfiguration.build)

      val cache = cacheManager.getCache[String, Lyrics]("singerLyricsCache")

      cache should have size (8)

      val stream =
        for (s <- Managed(cache.keySet.stream))
          yield s.collect(Collectors.toList[String])

      val keys = stream.acquireAndGet(identity)

      keys.forEach(k => cache.remove(k))

      cache should have size (8)
    }
}
