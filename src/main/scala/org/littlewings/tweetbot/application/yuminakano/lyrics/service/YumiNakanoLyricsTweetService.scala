package org.littlewings.tweetbot.application.yuminakano.lyrics.service

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.littlewings.tweetbot.application.yuminakano.lyrics.qualifier.YumiNakanoLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepository
import org.littlewings.tweetbot.standard.lyrics.service.{LyricsTweetService, LyricsTweetServiceSupport}
import twitter4j.Twitter

trait YumiNakanoLyricsTweetService extends LyricsTweetService

@Typed(Array(classOf[YumiNakanoLyricsTweetService]))
@ApplicationScoped
class DefaultYumiNakanoLyricsTweetService @Inject()(@YumiNakanoLyricsTweetBot override protected val twitter: Twitter,
                                                    @YumiNakanoLyricsTweetBot override protected val lyricsRepository: LyricsRepository)
  extends LyricsTweetServiceSupport with YumiNakanoLyricsTweetService
