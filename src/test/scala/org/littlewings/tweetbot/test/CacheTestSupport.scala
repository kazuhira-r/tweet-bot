package org.littlewings.tweetbot.test

import org.infinispan.configuration.global.GlobalConfigurationBuilder
import org.infinispan.manager.{DefaultCacheManager, EmbeddedCacheManager}
import org.junit.{After, Before}

trait CacheTestSupport {
  private var cacheManager: EmbeddedCacheManager = _

  protected def getCacheManager: EmbeddedCacheManager = {
    if (cacheManager == null) {
      val configurationBuilder = new GlobalConfigurationBuilder
      configurationBuilder
        .globalJmxStatistics()
        .allowDuplicateDomains(true)

      val m = new DefaultCacheManager(configurationBuilder.build)
      cacheManager = m
      m
    } else {
      cacheManager
    }
  }

  @After
  def tearDownCacheManager(): Unit = {
    cacheManager.stop()
    cacheManager = null
  }
}
