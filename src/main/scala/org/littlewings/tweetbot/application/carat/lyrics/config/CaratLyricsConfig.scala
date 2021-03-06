package org.littlewings.tweetbot.application.carat.lyrics.config

import javax.enterprise.context.ApplicationScoped

import com.typesafe.config.{Config, ConfigFactory}
import org.littlewings.tweetbot.config.EncryptTwitterConfigSupport

@ApplicationScoped
class CaratLyricsConfig extends EncryptTwitterConfigSupport {
  override protected val config: Config =
    ConfigFactory.load("job-data/carat/artist.conf").getConfig("artist")
}
