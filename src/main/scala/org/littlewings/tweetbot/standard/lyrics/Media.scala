package org.littlewings.tweetbot.standard.lyrics

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object MediaFactory extends ConfigCharacterEscaper {
  def create(artist: Artist, mediaNameAlias: String): Media = {
    val config =
      ConfigFactory
        .load(s"job-data/${artist.alias}/media/${mediaNameAlias}.conf")
        .getConfig("media")

    val name = config.getString("name")
    val numbering = config.getString("numbering")
    val mediaType = config.getString("type")

    val tracks = config.getStringList("tracks").asScala

    val trackLyrics =
      tracks
        .map(track => (track -> config.getStringList(escape(track)).asScala))
        .toMap

    Media(name, mediaNameAlias, mediaType, numbering, trackLyrics)
  }
}

case class Media(name: String, alias: String, mediaType: String, numbering: String, trackLyrics: Map[String, Seq[String]])