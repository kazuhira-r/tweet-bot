package org.littlewings.tweetbot.application.carat.lyrics.repository

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.infinispan.Cache
import org.littlewings.tweetbot.application.carat.lyrics.qualifier.CaratLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepositorySupport

@CaratLyricsTweetBot
@ApplicationScoped
class CaratLyricsRepository extends LyricsRepositorySupport {
  @CaratLyricsTweetBot
  @Inject
  override protected var cache: Cache[String, Lyrics] = _
}
