package org.littlewings.tweetbot.application.carat.lyrics.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.{Disposes, Produces}

import org.infinispan.Cache
import org.infinispan.configuration.cache.ConfigurationBuilder
import org.infinispan.manager.EmbeddedCacheManager
import org.littlewings.tweetbot.application.carat.lyrics.qualifier.CaratLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.standard.lyrics.cachestore.LyricsCacheStoreConfigurationBuilder

@Dependent
class CacheProducer {
  private[producer] val cacheName: String = "caratLyricsCache"

  @CaratLyricsTweetBot
  @ApplicationScoped
  @Produces
  def cache(cacheManager: EmbeddedCacheManager): Cache[String, Lyrics] = {
    val cacheConfiguration = new ConfigurationBuilder
    cacheConfiguration
      .persistence
      .addStore(classOf[LyricsCacheStoreConfigurationBuilder])
      .addProperty("artistNameAlias", "carat")

    cacheManager.defineConfiguration(cacheName, cacheConfiguration.build)
    cacheManager.getCache(cacheName)
  }

  def destroy(@CaratLyricsTweetBot @Disposes cache: Cache[String, Lyrics]): Unit = cache.stop()
}
