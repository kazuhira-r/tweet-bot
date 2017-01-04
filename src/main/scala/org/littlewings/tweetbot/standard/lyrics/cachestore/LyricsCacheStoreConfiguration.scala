package org.littlewings.tweetbot.standard.lyrics.cachestore

import org.infinispan.commons.configuration.attributes.AttributeSet
import org.infinispan.commons.configuration.{BuiltBy, ConfigurationFor}
import org.infinispan.configuration.cache.{AbstractStoreConfiguration, AsyncStoreConfiguration, SingletonStoreConfiguration}

@BuiltBy(classOf[LyricsCacheStoreConfigurationBuilder])
@ConfigurationFor(classOf[LyricsCacheStore])
class LyricsCacheStoreConfiguration(attributes: AttributeSet, async: AsyncStoreConfiguration, singletonStore: SingletonStoreConfiguration)
  extends AbstractStoreConfiguration(attributes, async, singletonStore) {

  val artistNameAlias: String = properties.getProperty("artistNameAlias")
}

