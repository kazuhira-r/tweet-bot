package org.littlewings.tweetbot.application.carat.information.service

import java.time.LocalDate

import org.jsoup.Jsoup
import org.junit.Test
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.test.MockitoScalaBridge._
import org.littlewings.tweetbot.test.{ScalaTestJUnitTestSupport, TestResourceLoader}
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._

class WebSiteInformationExtractServiceTest extends ScalaTestJUnitTestSupport {
  @Test
  def extractInformations(): Unit = {
    val webSiteInformationExtractServiceSpy = spy(new DefaultWebSiteInformationExtractService(null))
    doSingleReturn(Jsoup.parse(TestResourceLoader.byClass(getClass, getClass.getSimpleName + "_index.html")))
      .when(webSiteInformationExtractServiceSpy)
      .loadDocument(any[String])
    doSingleReturn("http://localhost/")
      .when(webSiteInformationExtractServiceSpy)
      .webSiteUrl

    val expected = Array(
      Information(LocalDate.of(2016, 8, 24), "https://google.com/", "https://google.com/", "外部リンク追加"),
      Information(LocalDate.of(2016, 8, 23), "http://localhost/#live", "http://localhost/smp/live.html", "LIVE＆EVENT 更新"),
      Information(LocalDate.of(2016, 8, 18), "http://localhost/#media", "http://localhost/smp/media.html", "MEDIA 更新"),
      Information(LocalDate.of(2016, 8, 12), "http://localhost/biography.html", "http://localhost/smp/biography.html", "Biography 更新"),
      Information(LocalDate.of(2016, 8, 11), "http://localhost/discography.html", "http://localhost/smp/discography.html", "Discography 更新"),
      Information(LocalDate.of(2016, 8, 10), "http://localhost/#information-head", "http://localhost/smp/index.html#info", "Information 更新"),
      Information(LocalDate.of(2016, 8, 8), "http://localhost/profile.html", "http://localhost/smp/profile.html", "Profile 更新"),
      Information(LocalDate.of(2016, 8, 8), "http://localhost/#new-release", "http://localhost/smp/index.html#info", "NEW RELEASE 更新")
    )

    webSiteInformationExtractServiceSpy.extract should contain theSameElementsInOrderAs expected

    verify(webSiteInformationExtractServiceSpy).loadDocument(any[String])
  }
}
