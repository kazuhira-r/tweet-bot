package org.littlewings.tweetbot.application.carat.information.service

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.littlewings.tweetbot.application.carat.information.qualifier.CaratInformationTweetBot
import org.littlewings.tweetbot.tweet.TweetService
import twitter4j.Twitter

trait CaratInformationTweetService extends TweetService

@Typed(Array(classOf[CaratInformationTweetService]))
@ApplicationScoped
class DefaultCaratInformationTweetService @Inject()(@CaratInformationTweetBot override protected val twitter: Twitter)
  extends CaratInformationTweetService
