package org.littlewings.tweetbot.application.carat.information.service

import java.time.format.DateTimeFormatter
import java.time.{Clock, LocalDate, ZoneId, ZonedDateTime}

import org.junit.{Before, Test}
import org.littlewings.tweetbot.LoggerSupport
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.repository.InformationRepository
import org.littlewings.tweetbot.test.MockitoScalaBridge._
import org.littlewings.tweetbot.test.ScalaTestJUnitTestSupport
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._

class InformationUpdatedSearchServiceTest extends ScalaTestJUnitTestSupport with LoggerSupport {
  val informationRepositorySpy: InformationRepository = spy(classOf[InformationRepository])

  @Before
  def setUp(): Unit = {
    val initialData = Array(
      Information(LocalDate.of(2016, 8, 20), "http://localhost/#media", "http://localhost/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 19), "http://localhost/#media", "http://localhost/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 18), "http://localhost/#media", "http://localhost/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 8), "http://localhost/profile.html", "http://localhost/profile.html", "AP 変更"),
      Information(LocalDate.of(2016, 8, 8), "http://localhost/#new-release", "http://localhost/#new-release", "NEW RELEASE 更新")
    )

    doSingleReturn(Vector(initialData))
      .when(informationRepositorySpy)
      .findAll
    doNothing()
      .when(informationRepositorySpy)
      .saveAll(any[Seq[Information]])
  }

  @Test
  def findUpdated(): Unit = {
    val fixedNow = ZonedDateTime.parse("2016-08-23 00:00:00 Asia/Tokyo", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV"))
    val fixedClock = Clock.fixed(fixedNow.toInstant, ZoneId.of("Asia/Tokyo"))

    val informationUpdatedSearchServiceSpy = spy(new DefaultInformationUpdatedSearchService(informationRepositorySpy))
    doSingleReturn(fixedClock).when(informationUpdatedSearchServiceSpy).clock

    val compareInformations = Array(
      Information(LocalDate.of(2016, 8, 25), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 24), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 23), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 22), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 21), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 18), "http://localhost/#media", "http://localhost/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 8), "http://localhost/profile.html", "http://localhost/profile.html", "AP 変更"),
      Information(LocalDate.of(2016, 8, 8), "http://localhost/#new-release", "http://localhost/#new-release", "NEW RELEASE 更新")
    )

    val expected = Array(
      Information(LocalDate.of(2016, 8, 25), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 24), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 23), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 22), "http://localhost/#live", "http://localhost/#live", "LIVE＆EVENT 更新")
    )

    val newArrivals = informationUpdatedSearchServiceSpy.findUpdated(compareInformations)

    newArrivals should contain theSameElementsInOrderAs expected

    verify(informationUpdatedSearchServiceSpy).clock
  }
}
