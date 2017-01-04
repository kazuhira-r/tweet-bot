package org.littlewings.tweetbot

import javax.inject.Inject

import org.slf4j.Logger

trait LoggerSupport {
  @Inject
  protected var logger: Logger = _
}
