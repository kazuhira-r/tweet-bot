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
  override protected var twitter: Twitter = _
}
