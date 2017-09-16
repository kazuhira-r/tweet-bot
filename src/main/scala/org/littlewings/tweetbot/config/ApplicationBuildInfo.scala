package org.littlewings.tweetbot.config

import org.littlewings.tweetbot.resource.Managed

object ApplicationBuildInfo {
  def build(): ApplicationBuildInfo = {
    val applicationBuildInfo = Managed(Thread.currentThread().getContextClassLoader.getResourceAsStream("application-build-info.properties"))
    val properties = new java.util.Properties
    applicationBuildInfo.foreach(properties.load)
    new ApplicationBuildInfo(properties)
  }
}

class ApplicationBuildInfo(properties: java.util.Properties) {
  def builtTime: String = properties.getProperty("build-time")
}
