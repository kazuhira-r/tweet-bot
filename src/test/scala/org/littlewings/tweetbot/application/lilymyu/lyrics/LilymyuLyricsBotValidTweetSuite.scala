package org.littlewings.tweetbot.application.lilymyu.lyrics

import org.littlewings.tweetbot.standard.lyrics.LyricsBotValidTweetSuiteSupport

class LilymyuLyricsBotValidTweetSuite extends LyricsBotValidTweetSuiteSupport {
  test("validate all lyrics") {
    validateFullLyrics("lilymyu")
  }
}
