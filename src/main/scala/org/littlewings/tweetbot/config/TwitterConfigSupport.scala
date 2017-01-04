package org.littlewings.tweetbot.config

import com.typesafe.config.Config

trait TwitterConfigSupport {
  protected val config: Config
  protected def twitter: Config = config.getConfig("twitter")

  def twitterOAuthConsumerKey: String = twitter.getString("oauth-consumer-key")
  def twitterOAuthConsumerSecret: String = twitter.getString("oauth-consumer-secret")
  def twitterOAuthAccessToken: String = twitter.getString("oauth-access-token")
  def twitterOAuthAccessTokenSecret: String = twitter.getString("oauth-access-token-secret")
}
