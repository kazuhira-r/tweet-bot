package org.littlewings.tweetbot.application.carat.lyrics.service

import javax.inject.Inject

import org.jboss.shrinkwrap.api.spec.WebArchive
import org.junit.Test
import org.littlewings.tweetbot.test.{ArquillianDeploymentSupport, ArquillianTestSupport}

object CaratLyricsTweetServiceITests extends ArquillianDeploymentSupport {
  override protected def customizeArchive(webArchive: WebArchive): Unit =
    webArchive
      .addPackages(true, "org.littlewings.tweetbot.application.carat.lyrics.config")
      .addPackages(true, "org.littlewings.tweetbot.application.carat.lyrics.producer")
      .addPackages(true, "org.littlewings.tweetbot.application.carat.lyrics.qualifier")
      .addPackages(true, "org.littlewings.tweetbot.application.carat.lyrics.repository")
      .addPackages(true, "org.littlewings.tweetbot.application.carat.lyrics.service")
}

class CaratLyricsTweetServiceITests extends ArquillianTestSupport {
  @Inject
  var caratLyricsTweetService: CaratLyricsTweetService = _

  @Test
  def tweet(): Unit = {
    caratLyricsTweetService.autoPickTweet()
  }
}
