package org.littlewings.tweetbot.application.lilymyu.lyrics

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.apache.deltaspike.scheduler.api.Scheduled
import org.littlewings.tweetbot.LoggerSupport
import org.littlewings.tweetbot.application.lilymyu.lyrics.service.LilymyuLyricsTweetService
import org.quartz.{Job, JobExecutionContext}

@Scheduled(cronExpression = "{lilymyu.lyrics.tweet.schedule}", onStartup = false)
@ApplicationScoped
class LilymyuLyricsTweetJob extends Job with LoggerSupport {
  @Inject
  private[lyrics] var lilymyuLyricsTweetService: LilymyuLyricsTweetService = _

  override def execute(context: JobExecutionContext): Unit = {
    logger.info("start LilymyuLyricsTweet job")

    lilymyuLyricsTweetService.autoPickTweet()

    logger.info("end LilymyuLyricsTweet job")
  }
}
