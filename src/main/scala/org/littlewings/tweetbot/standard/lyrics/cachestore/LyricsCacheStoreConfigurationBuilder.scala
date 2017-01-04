package org.littlewings.tweetbot.standard.lyrics.cachestore

import org.infinispan.configuration.cache.{AbstractStoreConfiguration, AbstractStoreConfigurationBuilder, PersistenceConfigurationBuilder}

class LyricsCacheStoreConfigurationBuilder(builder: PersistenceConfigurationBuilder)
  extends AbstractStoreConfigurationBuilder[LyricsCacheStoreConfiguration, LyricsCacheStoreConfigurationBuilder](builder, AbstractStoreConfiguration.attributeDefinitionSet) {
  override def create: LyricsCacheStoreConfiguration =
    new LyricsCacheStoreConfiguration(attributes.protect, async.create, singletonStore.create)

  override def self: LyricsCacheStoreConfigurationBuilder = this
}
