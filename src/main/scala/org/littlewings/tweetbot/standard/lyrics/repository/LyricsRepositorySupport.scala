package org.littlewings.tweetbot.standard.lyrics.repository

import java.util.stream.Collectors

import org.infinispan.Cache
import org.littlewings.tweetbot.resource.Managed
import org.littlewings.tweetbot.standard.lyrics.Lyrics

trait LyricsRepositorySupport {
  protected var cache: Cache[String, Lyrics]

  def size: Int = cache.size

  def select(n: Int): (String, Lyrics) = {
    if (n <= cache.size) {
      val stream =
        for (s <- Managed(cache.entrySet.stream))
          yield s
            .map[(String, Lyrics)](e => (e.getKey -> e.getValue))
            .collect(Collectors.toList[(String, Lyrics)])

      val entries = stream.acquireAndGet(identity)
      entries.get(n)
    } else {
      null
    }
  }

  def delete(key: String): Unit = cache.remove(key)
}
