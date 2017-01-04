package org.littlewings.tweetbot.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.{Disposes, Produces}

import org.infinispan.configuration.global.GlobalConfigurationBuilder
import org.infinispan.manager.{DefaultCacheManager, EmbeddedCacheManager}

@Dependent
class CacheManagerProducer {
  @ApplicationScoped
  @Produces
  def cacheManager: EmbeddedCacheManager = {
    val configurationBuilder = new GlobalConfigurationBuilder
    configurationBuilder
      .globalJmxStatistics()
      .allowDuplicateDomains(true)

    new DefaultCacheManager(configurationBuilder.build)
  }

  def destroy(@Disposes cacheManager: EmbeddedCacheManager): Unit =
    cacheManager.stop()
}
