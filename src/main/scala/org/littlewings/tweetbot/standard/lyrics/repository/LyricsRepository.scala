package org.littlewings.tweetbot.standard.lyrics.repository

import java.util.stream.Collectors

import org.infinispan.Cache
import org.littlewings.tweetbot.resource.Managed
import org.littlewings.tweetbot.standard.lyrics.Lyrics

trait LyricsRepository {
  protected val cache: Cache[String, Lyrics]

  def size: Int

  def select(n: Int): (String, Lyrics)

  def delete(key: String): Unit
}

trait LyricsRepositorySupport extends LyricsRepository {
  protected val cache: Cache[String, Lyrics]

  override def size: Int = cache.size

  override def select(n: Int): (String, Lyrics) = {
    if (n <= cache.size) {
      val stream =
        for (s <- Managed(cache.entrySet.stream))
          yield s
            .map[(String, Lyrics)](e => e.getKey -> e.getValue)
            .collect(Collectors.toList[(String, Lyrics)])

      val entries = stream.acquireAndGet(identity)
      entries.get(n)
    } else {
      null
    }
  }

  override def delete(key: String): Unit = cache.remove(key)
}
