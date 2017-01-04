package org.littlewings.tweetbot.application.carat.information.service

import java.time.LocalDate
import javax.inject.Inject

import org.apache.deltaspike.testcontrol.api.mock.DynamicMockManager
import org.jsoup.Jsoup
import org.junit.Test
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.test.{CdiTestRunnerTestSupport, TestResourceLoader}
import org.mockito.Matchers._
import org.mockito.Mockito._

class WebSiteInformationExtractServiceTest extends CdiTestRunnerTestSupport {
  @Inject
  var mockManager: DynamicMockManager = _

  @Inject
  var webSiteInformationExtractService: WebSiteInformationExtractService = _

  @Test
  def extractInformations(): Unit = {
    val webSiteInformationExtractServiceSpy = spy(classOf[WebSiteInformationExtractService])
    doReturn(Jsoup.parse(TestResourceLoader.byClass(getClass, getClass.getSimpleName + "_index.html")))
      .when(webSiteInformationExtractServiceSpy)
      .loadDocument(any[String])

    mockManager.addMock(webSiteInformationExtractServiceSpy)

    val expected = Array(
      Information(LocalDate.of(2016, 8, 23), "http://www.carat-ue.jp/#live", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 18), "http://www.carat-ue.jp/#media", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 8), "http://www.carat-ue.jp/profile.html", "AP 変更"),
      Information(LocalDate.of(2016, 8, 8), "http://www.carat-ue.jp/#new-release", "NEW RELEASE 更新")
    )

    webSiteInformationExtractService.extract should contain theSameElementsInOrderAs (expected)

    verify(webSiteInformationExtractServiceSpy).loadDocument(any[String])
  }
}
