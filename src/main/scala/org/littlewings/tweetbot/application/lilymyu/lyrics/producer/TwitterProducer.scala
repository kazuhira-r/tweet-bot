package org.littlewings.tweetbot.application.lilymyu.lyrics.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.Produces
import javax.inject.Inject

import org.littlewings.tweetbot.application.lilymyu.lyrics.config.LilymyuLyricsConfig
import org.littlewings.tweetbot.application.lilymyu.lyrics.qualifier.LilymyuLyricsTweetBot
import org.littlewings.tweetbot.tweet.LoggingTwitterFactory
import org.littlewings.tweetbot.{ProductionStage, TestStage}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

object TwitterProducer {

  @Dependent
  class ActualTwitterProducer extends ProductionStage {

    @Inject
    private[producer] var lilymyuLyricsConfig: LilymyuLyricsConfig = _

    @LilymyuLyricsTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = {
      val configurationBuilder = new ConfigurationBuilder
      configurationBuilder
        .setOAuthConsumerKey(lilymyuLyricsConfig.twitterOAuthConsumerKey)
        .setOAuthConsumerSecret(lilymyuLyricsConfig.twitterOAuthConsumerSecret)
        .setOAuthAccessToken(lilymyuLyricsConfig.twitterOAuthAccessToken)
        .setOAuthAccessTokenSecret(lilymyuLyricsConfig.twitterOAuthAccessTokenSecret)

      val twitterFactory = new TwitterFactory(configurationBuilder.build)
      twitterFactory.getInstance
    }
  }

  @Dependent
  class LoggingTwitterProducer extends TestStage {
    @LilymyuLyricsTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = LoggingTwitterFactory.newLoggingTwitter
  }

}
