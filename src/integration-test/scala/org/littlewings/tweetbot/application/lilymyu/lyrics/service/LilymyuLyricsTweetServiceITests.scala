package org.littlewings.tweetbot.application.lilymyu.lyrics.service

import javax.inject.Inject

import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.Test
import org.junit.runner.RunWith
import org.littlewings.tweetbot.test.{ArquillianDeploymentSupport, ArquillianTestSupport}

object LilymyuLyricsTweetServiceITests extends ArquillianDeploymentSupport {
  override protected def customizeArchive(webArchive: WebArchive): Unit =
    webArchive
      .addPackages(true, "org.littlewings.tweetbot.application.lilymyu.lyrics.config")
      .addPackages(true, "org.littlewings.tweetbot.application.lilymyu.lyrics.producer")
      .addPackages(true, "org.littlewings.tweetbot.application.lilymyu.lyrics.qualifier")
      .addPackages(true, "org.littlewings.tweetbot.application.lilymyu.lyrics.repository")
      .addPackages(true, "org.littlewings.tweetbot.application.lilymyu.lyrics.service")
}

@RunWith(classOf[Arquillian])
class LilymyuLyricsTweetServiceITests extends ArquillianTestSupport {
  @Inject
  var lilymyuLyricsTweetService: LilymyuLyricsTweetService = _

  @Test
  def tweet(): Unit = {
    lilymyuLyricsTweetService.autoPickTweet()
  }
}
