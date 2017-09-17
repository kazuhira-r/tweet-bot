package org.littlewings.tweetbot.application.carat.information.config

import javax.enterprise.context.ApplicationScoped

import com.typesafe.config.{Config, ConfigFactory}
import org.littlewings.tweetbot.config.EncryptTwitterConfigSupport

@ApplicationScoped
class CaratInformationConfig extends EncryptTwitterConfigSupport {
  override protected val config: Config =
    ConfigFactory.load("job-data/carat/information.conf").getConfig("information")

  def webSiteUrl: String = config.getString("website-url")
}
