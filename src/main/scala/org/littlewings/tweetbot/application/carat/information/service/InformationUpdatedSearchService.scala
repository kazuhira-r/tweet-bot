package org.littlewings.tweetbot.application.carat.information.service

import java.time.{Clock, LocalDate, ZoneId}
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.littlewings.tweetbot.LoggerSupport
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.repository.InformationRepository

trait InformationUpdatedSearchService {
  def findUpdated(targets: Seq[Information]): Seq[Information]
}

@Typed(Array(classOf[InformationUpdatedSearchService]))
@ApplicationScoped
class DefaultInformationUpdatedSearchService @Inject()(private[service] val informationRepository: InformationRepository)
  extends InformationUpdatedSearchService with LoggerSupport {

  private[service] def clock: Clock = Clock.system(ZoneId.of("Asia/Tokyo"))

  override def findUpdated(targets: Seq[Information]): Seq[Information] = {
    val currentInformations = informationRepository.findAll

    val notContains = targets.filterNot(target => currentInformations.contains(target))

    if (notContains.isEmpty) {
      Vector.empty
    } else {
      val today = LocalDate.now(clock)
      val yesterday = today.plusDays(-1)

      logger.info("today = {}, yesterday = {}", Array(today, yesterday): _*)

      informationRepository.saveAll(targets)

      notContains.filter(target => target.updateDate.isAfter(yesterday) || target.updateDate == yesterday)
    }
  }

}
