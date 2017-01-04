package org.littlewings.tweetbot.test

import org.slf4j.LoggerFactory

trait LoggerInjectSupport {
  protected def injectLogger(target: AnyRef, loggerClass: Class[_]): Unit = {
    val logger = LoggerFactory.getLogger(loggerClass)

    val loggerField = loggerClass.getDeclaredField("logger")
    loggerField.setAccessible(true)

    loggerField.set(target, logger)
  }
}
