package org.littlewings.tweetbot.application.lilymyu.lyrics.service

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.littlewings.tweetbot.application.lilymyu.lyrics.qualifier.LilymyuLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepositorySupport
import org.littlewings.tweetbot.standard.lyrics.service.LyricsTweetServiceSupport
import twitter4j.Twitter

@ApplicationScoped
class LilymyuLyricsTweetService extends LyricsTweetServiceSupport {
  @LilymyuLyricsTweetBot
  @Inject
  override protected var twitter: Twitter = _

  @LilymyuLyricsTweetBot
  @Inject
  override protected var lyricsRepository: LyricsRepositorySupport = _
}
