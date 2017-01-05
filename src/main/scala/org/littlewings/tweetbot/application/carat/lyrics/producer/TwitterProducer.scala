package org.littlewings.tweetbot.application.carat.lyrics.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.Produces
import javax.inject.Inject

import org.littlewings.tweetbot.application.carat.lyrics.config.CaratLyricsConfig
import org.littlewings.tweetbot.application.carat.lyrics.qualifier.CaratLyricsTweetBot
import org.littlewings.tweetbot.tweet.LoggingTwitterFactory
import org.littlewings.tweetbot.{ProductionStage, TestStage}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

object TwitterProducer {

  @Dependent
  class ActualTwitterProducer @Inject()(private[producer] val caratLyricsConfig: CaratLyricsConfig)
    extends ProductionStage {

    @CaratLyricsTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = {
      val configurationBuilder = new ConfigurationBuilder
      configurationBuilder
        .setOAuthConsumerKey(caratLyricsConfig.twitterOAuthConsumerKey)
        .setOAuthConsumerSecret(caratLyricsConfig.twitterOAuthConsumerSecret)
        .setOAuthAccessToken(caratLyricsConfig.twitterOAuthAccessToken)
        .setOAuthAccessTokenSecret(caratLyricsConfig.twitterOAuthAccessTokenSecret)

      val twitterFactory = new TwitterFactory(configurationBuilder.build)
      twitterFactory.getInstance
    }
  }

  @Dependent
  class LoggingTwitterProducer extends TestStage {
    @CaratLyricsTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = LoggingTwitterFactory.newLoggingTwitter
  }

}
