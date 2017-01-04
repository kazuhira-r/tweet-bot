package org.littlewings.tweetbot.application.carat.lyrics.service

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.littlewings.tweetbot.application.carat.lyrics.qualifier.CaratLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepositorySupport
import org.littlewings.tweetbot.standard.lyrics.service.LyricsTweetServiceSupport
import twitter4j.Twitter

@ApplicationScoped
class CaratLyricsTweetService extends LyricsTweetServiceSupport {
  @CaratLyricsTweetBot
  @Inject
  override protected var twitter: Twitter = _

  @CaratLyricsTweetBot
  @Inject
  override protected var lyricsRepository: LyricsRepositorySupport = _
}
