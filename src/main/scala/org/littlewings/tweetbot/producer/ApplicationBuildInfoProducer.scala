package org.littlewings.tweetbot.producer

import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces
import javax.faces.bean.ApplicationScoped

import org.littlewings.tweetbot.config.ApplicationBuildInfo

@Dependent
class ApplicationBuildInfoProducer {
  @Produces
  @ApplicationScoped
  def manifest: ApplicationBuildInfo = ApplicationBuildInfo.build()
}
