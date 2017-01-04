package org.littlewings.tweetbot.application.carat.information.service

import java.time.format.DateTimeFormatter
import java.time.{Clock, LocalDate, ZoneId, ZonedDateTime}
import javax.inject.Inject

import org.apache.deltaspike.testcontrol.api.mock.DynamicMockManager
import org.junit.{Before, Test}
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.repository.InformationRepository
import org.littlewings.tweetbot.test.CdiTestRunnerTestSupport
import org.mockito.Mockito._

class InformationUpdatedSearchServiceTest extends CdiTestRunnerTestSupport {
  @Inject
  var mockManager: DynamicMockManager = _

  @Inject
  var informationUpdatedSearchService: InformationUpdatedSearchService = _

  @Inject
  var informationRepository: InformationRepository = _

  @Before
  def setUp(): Unit = {
    informationRepository.clear()

    val initialData = Array(
      Information(LocalDate.of(2016, 8, 20), "http://www.carat-ue.jp/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 19), "http://www.carat-ue.jp/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 18), "http://www.carat-ue.jp/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 8), "http://www.carat-ue.jp/profile.html", "AP 変更"),
      Information(LocalDate.of(2016, 8, 8), "http://www.carat-ue.jp/#new-release", "NEW RELEASE 更新")
    )

    informationRepository.saveAll(initialData)
  }

  @Test
  def findUpdated(): Unit = {
    val fixedNow = ZonedDateTime.parse("2016-08-23 00:00:00 Asia/Tokyo", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV"))
    val fixedClock = Clock.fixed(fixedNow.toInstant, ZoneId.of("Asia/Tokyo"))

    val informationUpdatedSearchServiceSpy = spy(classOf[InformationUpdatedSearchService])
    doReturn(fixedClock).when(informationUpdatedSearchServiceSpy).clock

    mockManager.addMock(informationUpdatedSearchServiceSpy)

    val compareInformations = Array(
      Information(LocalDate.of(2016, 8, 25), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 24), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 23), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 22), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 21), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 18), "http://www.carat-ue.jp/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 8), "http://www.carat-ue.jp/profile.html", "AP 変更"),
      Information(LocalDate.of(2016, 8, 8), "http://www.carat-ue.jp/#new-release", "NEW RELEASE 更新")
    )

    val expected = Array(
      Information(LocalDate.of(2016, 8, 25), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 24), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 23), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 22), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新")
    )

    val newArrivals = informationUpdatedSearchService.findUpdated(compareInformations)

    newArrivals should contain theSameElementsInOrderAs (expected)

    verify(informationUpdatedSearchServiceSpy).clock
  }

}
