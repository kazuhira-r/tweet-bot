package org.littlewings.tweetbot.application.carat.information.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.config.CaratInformationConfig

import scala.collection.JavaConverters._

trait WebSiteInformationExtractService {
  def extract: Seq[Information]
}

object DefaultWebSiteInformationExtractService {
  val devideUrlMap: Map[String, String] = Map(
    "#information-head" -> "smp/index.html#info",
    "#new-release" -> "smp/index.html#info",
    "#media" -> "smp/media.html",
    "#live" -> "smp/live.html",
    "profile.html" -> "smp/profile.html",
    "discography.html" -> "smp/discography.html",
    "biography.html" -> "smp/biography.html"
  ).withDefault(identity)
}

@Typed(Array(classOf[WebSiteInformationExtractService]))
@ApplicationScoped
class DefaultWebSiteInformationExtractService @Inject()(private[service] val caratInformationConfig: CaratInformationConfig)
  extends WebSiteInformationExtractService {

  private[service] def webSiteUrl: String = caratInformationConfig.webSiteUrl

  override def extract: Seq[Information] = {
    val document = loadDocument(webSiteUrl)

    val updateDates = document.select("#information dl dt span.f11")
    val updateContents = document.select("#information dl dd a")

    updateDates
      .asScala
      .map(span => span.text)
      .zip(updateContents.asScala.map(a => (a.attr("href"), a.text)))
      .map { case (date, (href, content)) =>
        val (absolutePcLink, absoluteSpLink) =
          if (href.startsWith("http")) {
            (href, href)
          } else {
            val relative =
              if (href.startsWith("/"))
                href.substring(1, href.length)
              else
                href

            val spLink = DefaultWebSiteInformationExtractService.devideUrlMap(relative)
            (webSiteUrl + relative, webSiteUrl + spLink)
          }

        val splittedDate = date.split("\\.")
        val (year, month, day) = (splittedDate(0), splittedDate(1), splittedDate(2))

        val revisedDate =
          Array(year,
            if (month.length < 2) "0" + month else month,
            if (day.length < 2) "0" + day else day)
            .mkString(".")

        Information(LocalDate.parse(revisedDate, DateTimeFormatter.ofPattern("yyyy.MM.dd")), absolutePcLink, absoluteSpLink, content)
      }.toVector
  }

  private[service] def loadDocument(url: String): Document = Jsoup.connect(url).get
}
