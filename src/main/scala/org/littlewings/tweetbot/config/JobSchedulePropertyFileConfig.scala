package org.littlewings.tweetbot.config

import org.apache.deltaspike.core.api.config.PropertyFileConfig

class JobSchedulePropertyFileConfig extends PropertyFileConfig {
  override def getPropertyFileName: String = "job-schedule.properties"

  override def isOptional: Boolean = false
}
