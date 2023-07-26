package org.littlewings.tweetbot.application.carat.lyrics

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.apache.deltaspike.scheduler.api.Scheduled
import org.littlewings.tweetbot.LoggerSupport
import org.littlewings.tweetbot.application.carat.lyrics.service.CaratLyricsTweetService
import org.quartz.{Job, JobExecutionContext}

//@Scheduled(cronExpression = "{carat.lyrics.tweet.schedule}", onStartup = false)
//@ApplicationScoped
class CaratLyricsTweetJob extends Job with LoggerSupport {
  @Inject
  private[lyrics] var caratLyricsTweetService: CaratLyricsTweetService = _

  override def execute(context: JobExecutionContext): Unit = {
    logger.info("start CaratLyricsTweet job")

    caratLyricsTweetService.autoPickTweet()

    logger.info("end CaratLyricsTweet job")
  }
}
