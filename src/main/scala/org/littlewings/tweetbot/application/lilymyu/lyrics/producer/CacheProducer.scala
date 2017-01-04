package org.littlewings.tweetbot.application.lilymyu.lyrics.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.{Disposes, Produces}

import org.infinispan.Cache
import org.infinispan.configuration.cache.ConfigurationBuilder
import org.infinispan.manager.EmbeddedCacheManager
import org.littlewings.tweetbot.application.lilymyu.lyrics.qualifier.LilymyuLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.standard.lyrics.cachestore.LyricsCacheStoreConfigurationBuilder

@Dependent
class CacheProducer {
  private[producer] val cacheName: String = "lilymyuLyricsCache"

  @LilymyuLyricsTweetBot
  @ApplicationScoped
  @Produces
  def cache(cacheManager: EmbeddedCacheManager): Cache[String, Lyrics] = {
    val cacheConfiguration = new ConfigurationBuilder
    cacheConfiguration
      .persistence
      .addStore(classOf[LyricsCacheStoreConfigurationBuilder])
      .addProperty("artistNameAlias", "lilymyu")

    cacheManager.defineConfiguration(cacheName, cacheConfiguration.build)
    cacheManager.getCache(cacheName)
  }

  def destroy(@LilymyuLyricsTweetBot @Disposes cache: Cache[String, Lyrics]): Unit = cache.stop()
}
