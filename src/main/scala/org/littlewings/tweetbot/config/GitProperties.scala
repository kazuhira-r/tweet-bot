package org.littlewings.tweetbot.config

import org.littlewings.tweetbot.resource.Managed

object GitProperties {
  def apply(gitPropertiesFilePath: String): GitProperties = {
    val properties = new java.util.Properties
    for (gitPropertiesStream <- Managed(classOf[GitProperties].getClassLoader.getResourceAsStream(gitPropertiesFilePath))) {
      properties.load(gitPropertiesStream)
    }

    new GitProperties(properties)
  }
}

class GitProperties(properties: java.util.Properties) {
  protected def get(key: String): String = properties.getProperty(key)

  def gitBuildTime: String = get("git.build.time")
  def gitCommitId: String = get("git.commit.id")
  def gitCommitTime: String = get("git.commit.time")
  def getCommitMessageShort: String = get("git.commit.message.short")
}
