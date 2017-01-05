package org.littlewings.tweetbot.application.lilymyu.lyrics.service

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.littlewings.tweetbot.application.lilymyu.lyrics.qualifier.LilymyuLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepository
import org.littlewings.tweetbot.standard.lyrics.service.{LyricsTweetService, LyricsTweetServiceSupport}
import twitter4j.Twitter

trait LilymyuLyricsTweetService extends LyricsTweetService

@Typed(Array(classOf[LilymyuLyricsTweetService]))
@ApplicationScoped
class DefaultLilymyuLyricsTweetService @Inject()(@LilymyuLyricsTweetBot override protected val twitter: Twitter,
                                                 @LilymyuLyricsTweetBot override protected val lyricsRepository: LyricsRepository)
  extends LyricsTweetServiceSupport with LilymyuLyricsTweetService
