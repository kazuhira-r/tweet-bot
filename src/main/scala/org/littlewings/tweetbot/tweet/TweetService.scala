package org.littlewings.tweetbot.tweet

import twitter4j.Twitter
import twitter4j.TwitterV2ExKt

trait TweetService {
  protected val twitter: Twitter

  def tweet(source: TweetSource): Unit =
    TwitterV2ExKt.getV2(twitter).createTweet(
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
