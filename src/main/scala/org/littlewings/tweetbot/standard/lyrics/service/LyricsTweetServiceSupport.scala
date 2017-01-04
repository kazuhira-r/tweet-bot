package org.littlewings.tweetbot.standard.lyrics.service

import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.inject.Inject

import org.littlewings.tweetbot.standard.lyrics.Lyrics
import org.littlewings.tweetbot.standard.lyrics.repository.LyricsRepositorySupport
import org.littlewings.tweetbot.tweet.TweetService
import org.slf4j.Logger
import twitter4j.Twitter

import scala.util.{Failure, Random, Success, Try}

object LyricsTweetServiceSupport {
  protected val random: Random = new Random(new SecureRandom)

  def nextInt(limit: Int): Int = random.nextInt(limit)
}

trait LyricsTweetServiceSupport extends TweetService {
  @Inject
  protected var logger: Logger = _

  protected var twitter: Twitter

  protected var lyricsRepository: LyricsRepositorySupport

  def autoPickTweet(): Unit = {
    (1 to 3).foldLeft(Try(action())) {
      case (s@Success(_), _) =>
        s
      case (Failure(e), i) =>
        val sleepTime = 3L

        logger.warn("tweet failure, fail count = {}", i, e)
        logger.info("sleep... {} sec", sleepTime)
        TimeUnit.SECONDS.sleep(sleepTime)

        Try(action())
    }
  }

  protected def action: () => Unit =
    () => {
      val (key, lyrics) = pickLyrics(LyricsTweetServiceSupport.nextInt(currentLyricsSize))

      logger.info("tweet: stored-size = {}, artist = {}, album = {}, track = {}",
        currentLyricsSize.toString, lyrics.artist, lyrics.album, lyrics.trackName)

      deleteLyrics(key)
      tweet(lyrics)
    }

  protected def tweetTo(message: String): Unit =
    twitter.updateStatus(message)

  protected def pickLyrics(n: Int): (String, Lyrics) =
    lyricsRepository.select(n)

  protected def currentLyricsSize: Int = lyricsRepository.size

  protected def deleteLyrics(key: String): Unit =
    lyricsRepository.delete(key)
}
