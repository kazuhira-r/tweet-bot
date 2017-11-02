package org.littlewings.tweetbot.application.yuminakano.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.Produces
import javax.inject.Inject

import org.littlewings.tweetbot.application.yuminakano.lyrics.config.YumiNakanoLyricsConfig
import org.littlewings.tweetbot.application.yuminakano.lyrics.qualifier.YumiNakanoLyricsTweetBot
import org.littlewings.tweetbot.tweet.LoggingTwitterFactory
import org.littlewings.tweetbot.{ProductionStage, TestStage}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

object TwitterProducer {

  @Dependent
  class ActualTwitterProducer @Inject()(private[producer] val yumiNakanoLyricsConfig: YumiNakanoLyricsConfig)
    extends ProductionStage {

    @YumiNakanoLyricsTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = {
      val configurationBuilder = new ConfigurationBuilder
      configurationBuilder
        .setOAuthConsumerKey(yumiNakanoLyricsConfig.twitterOAuthConsumerKey)
        .setOAuthConsumerSecret(yumiNakanoLyricsConfig.twitterOAuthConsumerSecret)
        .setOAuthAccessToken(yumiNakanoLyricsConfig.twitterOAuthAccessToken)
        .setOAuthAccessTokenSecret(yumiNakanoLyricsConfig.twitterOAuthAccessTokenSecret)

      val twitterFactory = new TwitterFactory(configurationBuilder.build)
      twitterFactory.getInstance
    }
  }

  @Dependent
  class LoggingTwitterProducer extends TestStage {
    @YumiNakanoLyricsTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = LoggingTwitterFactory.newLoggingTwitter
  }

}
