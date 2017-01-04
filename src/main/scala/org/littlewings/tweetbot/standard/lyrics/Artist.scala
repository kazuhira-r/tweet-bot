package org.littlewings.tweetbot.standard.lyrics

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object ArtistFactory {
  def create(artistNameAlias: String): Artist = {
    val config =
      ConfigFactory
        .load(s"job-data/${artistNameAlias}/artist.conf")
        .getConfig("artist")

    val name = config.getString("name")
    val media = config.getStringList("media").asScala

    Artist(name, artistNameAlias, media)
  }
}

case class Artist(name: String, alias: String, media: Seq[String])