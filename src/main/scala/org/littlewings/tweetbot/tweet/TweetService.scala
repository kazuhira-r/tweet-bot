package org.littlewings.tweetbot.tweet

import twitter4j.Twitter

trait TweetService {
  protected val twitter: Twitter

  def tweet(source: TweetSource): Unit =
    twitter.updateStatus(source.format)
}
