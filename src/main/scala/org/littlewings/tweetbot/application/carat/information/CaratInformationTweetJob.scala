package org.littlewings.tweetbot.application.carat.information

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.apache.deltaspike.scheduler.api.Scheduled
import org.littlewings.tweetbot.LoggerSupport
import org.littlewings.tweetbot.application.carat.information.service.{CaratInformationTweetService, InformationUpdatedSearchService, WebSiteInformationExtractService}
import org.quartz.{Job, JobExecutionContext}

//@Scheduled(cronExpression = "{carat.information.tweet.schedule}", onStartup = false)
//@ApplicationScoped
class CaratInformationTweetJob extends Job with LoggerSupport {
  @Inject
  private[information] var webSiteInformationExtractService: WebSiteInformationExtractService = _

  @Inject
  private[information] var informationUpdatedSearchService: InformationUpdatedSearchService = _

  @Inject
  private[information] var caratInformationTweetService: CaratInformationTweetService = _

  override def execute(context: JobExecutionContext): Unit = {
    logger.info("start CaratInformationTweet job")

    val informations = webSiteInformationExtractService.extract
    logger.info("extract informations = {}", informations.size)

    val newInformations = informationUpdatedSearchService.findUpdated(informations)
    logger.info("new arrival informations = {}", newInformations.size)

    newInformations.foreach { newInformation =>
      caratInformationTweetService.tweet(newInformation)
    }

    logger.info("end CaratInformationTweet job")
  }
}
