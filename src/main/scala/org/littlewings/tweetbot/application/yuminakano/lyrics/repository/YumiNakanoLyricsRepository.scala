package org.littlewings.tweetbot.application.yuminakano.lyrics.repository

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.infinispan.Cache
import org.littlewings.tweetbot.application.yuminakano.lyrics.qualifier.YumiNakanoLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepositorySupport

@YumiNakanoLyricsTweetBot
@ApplicationScoped
class YumiNakanoLyricsRepository @Inject()(@YumiNakanoLyricsTweetBot override protected val cache: Cache[String, Lyrics])
  extends LyricsRepositorySupport
