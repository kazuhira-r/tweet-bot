package org.littlewings.tweetbot.application.carat.information.producer

import javax.enterprise.context.{ApplicationScoped, Dependent}
import javax.enterprise.inject.Produces
import javax.inject.Inject

import org.littlewings.tweetbot.application.carat.information.config.CaratInformationConfig
import org.littlewings.tweetbot.application.carat.information.qualifier.CaratInformationTweetBot
import org.littlewings.tweetbot.tweet.LoggingTwitterFactory
import org.littlewings.tweetbot.{ProductionStage, TestStage}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

object TwitterProducer {

  @Dependent
  class ActualTwitterProducer @Inject()(private[producer] val caratInformationConfig: CaratInformationConfig)
    extends ProductionStage {

    @CaratInformationTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = {
      val configurationBuilder = new ConfigurationBuilder
      configurationBuilder
        .setOAuthConsumerKey(caratInformationConfig.twitterOAuthConsumerKey)
        .setOAuthConsumerSecret(caratInformationConfig.twitterOAuthConsumerSecret)
        .setOAuthAccessToken(caratInformationConfig.twitterOAuthAccessToken)
        .setOAuthAccessTokenSecret(caratInformationConfig.twitterOAuthAccessTokenSecret)

      val twitterFactory = new TwitterFactory(configurationBuilder.build)
      twitterFactory.getInstance
    }
  }

  @Dependent
  class LoggingTwitterProducer extends TestStage {
    @CaratInformationTweetBot
    @ApplicationScoped
    @Produces
    def twitter: Twitter = LoggingTwitterFactory.newLoggingTwitter
  }

}
