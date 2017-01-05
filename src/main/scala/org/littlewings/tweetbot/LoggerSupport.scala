package org.littlewings.tweetbot

import org.slf4j.{Logger, LoggerFactory}

object LoggerSupport {
  def loggerFor(clazz: Class[_]): Logger =
    if (clazz.getName.contains("$" + "Proxy" + "$"))
      LoggerFactory.getLogger(clazz.getSuperclass)
    else
      LoggerFactory.getLogger(clazz)
}

trait LoggerSupport {
  protected val logger: Logger = LoggerSupport.loggerFor(getClass)
}
