package org.littlewings.tweetbot.application.carat.information.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.junit.Test
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.test.ScalaTestJUnitTestSupport

class InformationFormatTest extends ScalaTestJUnitTestSupport {
  @Test
  def informationFormat(): Unit = {
    val updateDate = LocalDate.parse("2016.08.23", DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val pcLinkUrl = "http://localhost/index.html"
    val spLinkUrl = "http://localhost/smp/index.html"
    val content = "テストコンテンツが更新されました。"

    val information = Information(updateDate, pcLinkUrl, spLinkUrl, content)

    information.format should be(
      s"""|[2016.08.23 オフィシャルサイト更新]
          |テストコンテンツが更新されました。
          |
          |スマートフォン↓
          |http://localhost/smp/index.html
          |
          |PC↓
          |http://localhost/index.html""".stripMargin)
  }

  @Test
  def informationFormatPcAndSpSameUrl(): Unit = {
    val updateDate = LocalDate.parse("2016.08.23", DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val linkUrl = "http://localhost/index.html"
    val content = "テストコンテンツが更新されました。"

    val information = Information(updateDate, linkUrl, linkUrl, content)

    information.format should be(
      s"""|[2016.08.23 オフィシャルサイト更新]
          |テストコンテンツが更新されました。
          |
         |http://localhost/index.html""".stripMargin)
  }
}
