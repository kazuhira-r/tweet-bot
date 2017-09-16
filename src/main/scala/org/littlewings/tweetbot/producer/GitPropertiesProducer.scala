package org.littlewings.tweetbot.producer

import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces
import javax.faces.bean.ApplicationScoped

import org.littlewings.tweetbot.config.GitProperties

@Dependent
class GitPropertiesProducer {
  @Produces
  @ApplicationScoped
  def gitProperties: GitProperties = GitProperties("git.properties")
}
