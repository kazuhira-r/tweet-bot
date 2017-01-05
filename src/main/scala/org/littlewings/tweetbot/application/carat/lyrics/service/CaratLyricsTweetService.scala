package org.littlewings.tweetbot.application.carat.lyrics.service

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.littlewings.tweetbot.application.carat.lyrics.qualifier.CaratLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepository
import org.littlewings.tweetbot.standard.lyrics.service.{LyricsTweetService, LyricsTweetServiceSupport}
import twitter4j.Twitter

trait CaratLyricsTweetService extends LyricsTweetService

@Typed(Array(classOf[CaratLyricsTweetService]))
@ApplicationScoped
class DefaultCaratLyricsTweetService @Inject()(@CaratLyricsTweetBot override protected val twitter: Twitter,
                                               @CaratLyricsTweetBot override protected val lyricsRepository: LyricsRepository)
  extends LyricsTweetServiceSupport with CaratLyricsTweetService