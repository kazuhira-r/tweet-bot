package org.littlewings.tweetbot.application.carat.information

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.littlewings.tweetbot.tweet.TweetSource

case class Information(updateDate: LocalDate, pcLinkUrl: String, spLinkUrl: String, updateContent: String) extends TweetSource {
  override def format: String =
    if (pcLinkUrl != spLinkUrl)
      s"""|[${updateDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))} オフィシャルサイト更新]
          |${updateContent}
          |
        |スマートフォン↓
          |${spLinkUrl}
          |
        |PC↓
          |${pcLinkUrl}""".stripMargin
    else
      s"""|[${updateDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))} オフィシャルサイト更新]
          |${updateContent}
          |
          |${pcLinkUrl}""".stripMargin
}
