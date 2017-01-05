package org.littlewings.tweetbot.application.carat.lyrics.repository

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.infinispan.Cache
import org.littlewings.tweetbot.application.carat.lyrics.qualifier.CaratLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.standard.lyrics.repository.{LyricsRepository, LyricsRepositorySupport}

@Typed(Array(classOf[LyricsRepository]))
@CaratLyricsTweetBot
@ApplicationScoped
class CaratLyricsRepository @Inject()(@CaratLyricsTweetBot override protected val cache: Cache[String, Lyrics])
  extends LyricsRepositorySupport