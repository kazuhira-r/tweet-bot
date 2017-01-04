package org.littlewings.tweetbot.tweet

trait TweetService {
  def tweet(source: TweetSource): Unit =
    tweetTo(source.format)

  protected def tweetTo(message: String): Unit
}
