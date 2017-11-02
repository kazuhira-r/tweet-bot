package org.littlewings.tweetbot.application.yuminakano.lyrics

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.apache.deltaspike.scheduler.api.Scheduled
import org.littlewings.tweetbot.LoggerSupport
import org.littlewings.tweetbot.application.yuminakano.lyrics.service.YumiNakanoLyricsTweetService
import org.quartz.{Job, JobExecutionContext}

@Scheduled(cronExpression = "{yumi-nakano.lyrics.tweet.schedule}", onStartup = false)
@ApplicationScoped
class YumiNakanoLyricsTweetJob extends Job with LoggerSupport {
  @Inject
  private[lyrics] var yumiNakanoLyricsTweetService: YumiNakanoLyricsTweetService = _

  override def execute(context: JobExecutionContext): Unit = {
    logger.info("start YumiNakanoLyricsTweet job")

    yumiNakanoLyricsTweetService.autoPickTweet()

    logger.info("end YumiNakanoLyricsTweet job")
  }
}
