package org.littlewings.tweetbot.application.yuminakano.lyrics.config

import javax.enterprise.context.ApplicationScoped

import com.typesafe.config.{Config, ConfigFactory}
import org.littlewings.tweetbot.config.EncryptTwitterConfigSupport

@ApplicationScoped
class YumiNakanoLyricsConfig extends EncryptTwitterConfigSupport {
  override protected val config: Config =
    ConfigFactory.load("job-data/yumi-nakano/artist.conf").getConfig("artist")
}
