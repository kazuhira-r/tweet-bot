package org.littlewings.tweetbot.application.carat.lyrics.config

import javax.enterprise.context.ApplicationScoped

import com.typesafe.config.{Config, ConfigFactory}
import org.littlewings.tweetbot.config.TwitterConfigSupport

@ApplicationScoped
class CaratLyricsConfig extends TwitterConfigSupport {
  override protected val config: Config =
    ConfigFactory.load("job-data/carat/lyrics.conf").getConfig("artist")
}
