package org.littlewings.tweetbot.standard.lyrics.cachestore

import java.util.UUID

import org.infinispan.commons.configuration.ConfiguredBy
import org.infinispan.persistence.spi.InitializationContext
import org.littlewings.tweetbot.cachestore.AutoReloadableInMemoryCacheStore
import org.littlewings.tweetbot.standard.lyrics.{Lyrics, LyricsFactory}

@ConfiguredBy(classOf[LyricsCacheStoreConfiguration])
class LyricsCacheStore extends AutoReloadableInMemoryCacheStore[String, Lyrics] {
  private[cachestore] var artistNameAlias: String = _

  override protected def reload: Map[String, Lyrics] = {
    val lyrics = LyricsFactory.buildLyrics(artistNameAlias)

    lyrics
      .map(l => (UUID.randomUUID.toString -> l))
      .toMap
  }

  override def init(ctx: InitializationContext): Unit = {
    super.init(ctx)

    artistNameAlias = context.getConfiguration[LyricsCacheStoreConfiguration].artistNameAlias
  }
}
