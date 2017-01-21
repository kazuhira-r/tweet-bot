package org.littlewings.tweetbot.standard.lyrics

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

object LyricsBotValidTweetSuiteSupport {
  val tweetMaxLength: Int = 140
}

@RunWith(classOf[JUnitRunner])
trait LyricsBotValidTweetSuiteSupport extends FunSuite with GivenWhenThen with Matchers {
  protected def validateFullLyrics(artistNameAlias: String): Unit = {
    Given(s"a artist[${artistNameAlias}]")

    val lyrics = LyricsFactory.buildLyrics(artistNameAlias)

    lyrics.foreach { l =>
      val formatted = l.format

      info("=================================================================")
      Then(s"## album ${l.album}, track ${l.trackName}, size = ${formatted.length}")
      info("")

      info("\"lyrics\"")
      info(formatted)

      formatted.length should be <= LyricsBotValidTweetSuiteSupport.tweetMaxLength

      info(s"## album ${l.album}, track ${l.trackName} OK!!")
      info("")
    }

    info(s"artist[${artistNameAlias}] all lyrics passed. (${lyrics.size})")
  }
}
