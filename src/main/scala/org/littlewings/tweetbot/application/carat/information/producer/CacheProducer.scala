package org.littlewings.tweetbot.application.carat.information.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.{Disposes, Produces}

import org.infinispan.Cache
import org.infinispan.manager.EmbeddedCacheManager
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.qualifier.CaratInformationTweetBot

@Dependent
class CacheProducer {
  @CaratInformationTweetBot
  @ApplicationScoped
  @Produces
  def cache(cacheManager: EmbeddedCacheManager): Cache[String, Information] =
    cacheManager.getCache("caratInformationCache")

  def destroy(@CaratInformationTweetBot @Disposes cache: Cache[String, Information]): Unit = cache.stop()
}
