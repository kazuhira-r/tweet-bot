package org.littlewings.tweetbot.application.carat.lyrics.service

import org.junit.Test
import org.littlewings.tweetbot.test.JaxrsServerTestSupport

class CaratLyricsTweetServiceITests extends JaxrsServerTestSupport {
  @Test
  def tweet(): Unit =
    withServer { _ =>
      injectedBean(classOf[CaratLyricsTweetService]).autoPickTweet()
    }
}
