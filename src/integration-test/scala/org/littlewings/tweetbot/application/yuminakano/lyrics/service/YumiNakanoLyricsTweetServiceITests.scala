package org.littlewings.tweetbot.application.yuminakano.lyrics.service

import org.junit.Test
import org.littlewings.tweetbot.test.JaxrsServerTestSupport

class YumiNakanoLyricsTweetServiceITests extends JaxrsServerTestSupport {
  @Test
  def tweet(): Unit =
    withServer { _ =>
      injectedBean(classOf[YumiNakanoLyricsTweetService]).autoPickTweet()
    }
}
