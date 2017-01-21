package org.littlewings.tweetbot.application.carat.lyrics

import org.littlewings.tweetbot.standard.lyrics.LyricsBotValidTweetSuiteSupport

class CaratLyricsBotValidTweetSuite extends LyricsBotValidTweetSuiteSupport {
  test("validate all lyrics") {
    validateFullLyrics("carat")
  }
}
