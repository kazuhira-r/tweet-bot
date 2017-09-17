package org.littlewings.tweetbot.application.lilymyu.lyrics.service

import org.junit.Test
import org.littlewings.tweetbot.test.JaxrsServerTestSupport

class LilymyuLyricsTweetServiceITests extends JaxrsServerTestSupport {
  @Test
  def tweet(): Unit =
    withServer { _ =>
      injectedBean(classOf[LilymyuLyricsTweetService]).autoPickTweet()
    }
}
