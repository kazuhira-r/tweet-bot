package org.littlewings.tweetbot.application.carat.information.service

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.littlewings.tweetbot.application.carat.information.qualifier.CaratInformationTweetBot
import org.littlewings.tweetbot.tweet.TweetService
import twitter4j.Twitter

@ApplicationScoped
class CaratInformationTweetService extends TweetService {
  @CaratInformationTweetBot
  @Inject
  private[service] var twitter: Twitter = _

  override protected def tweetTo(message: String): Unit =
    twitter.updateStatus(message)
}
