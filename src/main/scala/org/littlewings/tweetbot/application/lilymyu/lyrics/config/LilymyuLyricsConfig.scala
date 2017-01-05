package org.littlewings.tweetbot.application.lilymyu.lyrics.config

import javax.enterprise.context.ApplicationScoped

import com.typesafe.config.{Config, ConfigFactory}
import org.littlewings.tweetbot.config.TwitterConfigSupport

@ApplicationScoped
class LilymyuLyricsConfig extends TwitterConfigSupport {
  override protected val config: Config =
    ConfigFactory.load("job-data/lilymyu/artist.conf").getConfig("artist")
}
