package org.littlewings.tweetbot.application.lilymyu.lyrics.repository

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.infinispan.Cache
import org.littlewings.tweetbot.application.lilymyu.lyrics.qualifier.LilymyuLyricsTweetBot
import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepositorySupport

@LilymyuLyricsTweetBot
@ApplicationScoped
class LilymyuLyricsRepository @Inject()(@LilymyuLyricsTweetBot override protected val cache: Cache[String, Lyrics])
  extends LyricsRepositorySupport
