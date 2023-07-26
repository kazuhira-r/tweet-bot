package org.littlewings.tweetbot.tweet

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory, TwitterV2ExKt}

trait TweetService {
  protected val twitter: Twitter

  def tweet(source: TweetSource): Unit = {
    val configuration = twitter.getConfiguration
    val configurationBuilder = new ConfigurationBuilder
    configurationBuilder
      .setOAuthConsumerKey(configuration.getOAuthConsumerKey)
      .setOAuthConsumerSecret(configuration.getOAuthConsumerSecret)
      .setOAuthAccessToken(configuration.getOAuthAccessToken)
      .setOAuthAccessTokenSecret(configuration.getOAuthAccessTokenSecret)

    val twitterFactory = new TwitterFactory(configurationBuilder.build)
    val realTwitter = twitterFactory.getInstance

    TwitterV2ExKt.getV2(realTwitter).createTweet(
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      source.format
    )
  }
}
