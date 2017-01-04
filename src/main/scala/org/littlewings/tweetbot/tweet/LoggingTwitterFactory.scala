package org.littlewings.tweetbot.tweet

import org.slf4j.LoggerFactory
import twitter4j.Twitter

object LoggingTwitterFactory {
  def newLoggingTwitter: Twitter =
    java.lang.reflect.Proxy
      .newProxyInstance(getClass.getClassLoader,
        Array(classOf[Twitter]),
        (proxy, method, args) => {
          val logger = LoggerFactory.getLogger(getClass)

          method.getName match {
            case "updateStatus" =>
              logger.info("tweet = {}", args(0))
              null
            case _ => null
          }
        }
      ).asInstanceOf[Twitter]
}
