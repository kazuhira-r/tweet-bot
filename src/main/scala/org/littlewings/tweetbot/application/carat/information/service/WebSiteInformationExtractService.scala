package org.littlewings.tweetbot.application.carat.information.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.config.CaratInformationConfig

import scala.collection.JavaConverters._

@ApplicationScoped
class WebSiteInformationExtractService {
  @Inject
  private[service] var caratInformationConfig: CaratInformationConfig = _

  private[service] def webSiteUrl: String = caratInformationConfig.webSiteUrl

  def extract: Seq[Information] = {
    val document = loadDocument(webSiteUrl)

    val updateDates = document.select("#information dl dt span.f11")
    val updateContents = document.select("#information dl dd a")

    updateDates
      .asScala
      .map(span => span.text)
      .zip(updateContents.asScala.map(a => (a.attr("href"), a.text)))
      .map { case (date, (href, content)) =>
        val absoluteLink =
          if (href.startsWith("http"))
            href
          else if (href.startsWith("/"))
            webSiteUrl + href.substring(1, href.length)
          else
            webSiteUrl + href

        val splittedDate = date.split("\\.")
        val (year, month, day) = (splittedDate(0), splittedDate(1), splittedDate(2))

        val revisedDate =
          Array(year,
            if (month.length < 2) "0" + month else month,
            if (day.length < 2) "0" + day else day)
            .mkString(".")

        Information(LocalDate.parse(revisedDate, DateTimeFormatter.ofPattern("yyyy.MM.dd")), absoluteLink, content)
      }.toVector
  }

  private[service] def loadDocument(url: String): Document = Jsoup.connect(url).get
}
