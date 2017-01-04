package org.littlewings.tweetbot.standard.lyrics

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

object LyricsBotValidTweetSuiteSupport {
  val tweetMaxLength: Int = 140
}

@RunWith(classOf[JUnitRunner])
trait LyricsBotValidTweetSuiteSupport extends FunSpec with GivenWhenThen with Matchers {
  protected def validateFullLyrics(artistNameAlias: String): Unit = {
    Given(s"a artist[${artistNameAlias}]")

    val lyrics = LyricsFactory.buildLyrics(artistNameAlias)

    lyrics.foreach { l =>
      val formatted = l.format

      info("=================================================================")
      Then(s"## album ${l.album}, track ${l.trackName}, size = ${formatted.size}")
      info("")

      info("\"lyrics\"")
      info(formatted)

      formatted.size should be <= LyricsBotValidTweetSuiteSupport.tweetMaxLength

      info(s"## album ${l.album}, track ${l.trackName} OK!!")
      info("")
    }

    info(s"artist[${artistNameAlias}] all lyrics passed. (${lyrics.size})")
  }
}
