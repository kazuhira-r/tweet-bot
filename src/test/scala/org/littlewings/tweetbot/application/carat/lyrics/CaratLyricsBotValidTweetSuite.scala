package org.littlewings.tweetbot.application.carat.lyrics

import org.littlewings.tweetbot.standard.lyrics.LyricsBotValidTweetSuiteSupport

class CaratLyricsBotValidTweetSuite extends LyricsBotValidTweetSuiteSupport {
  describe("CaratLyricsTweetBot") {
    it("validate all lyrics") {
      validateFullLyrics("carat")
    }
  }
}
