package org.littlewings.tweetbot.config

import com.typesafe.config.Config
import org.apache.deltaspike.core.api.projectstage.ProjectStage
import org.apache.deltaspike.core.util.ProjectStageProducer

trait TwitterConfigSupport {
  protected val config: Config
  protected def twitter: Config = {
    if (ProjectStageProducer.getInstance.getProjectStage == ProjectStage.Production)
      config.getConfig("twitter")
    else
      config.getConfig("twitter").getConfig("development")
  }

  def twitterOAuthConsumerKey: String = twitter.getString("oauth-consumer-key")
  def twitterOAuthConsumerSecret: String = twitter.getString("oauth-consumer-secret")
  def twitterOAuthAccessToken: String = twitter.getString("oauth-access-token")
  def twitterOAuthAccessTokenSecret: String = twitter.getString("oauth-access-token-secret")
}
