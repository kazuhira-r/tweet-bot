package org.littlewings.tweetbot.rest

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.MediaType
import javax.ws.rs.{GET, Path, Produces}

import org.littlewings.tweetbot.config.{ApplicationBuildInfo, GitProperties}

import scala.collection.mutable

@Path("info")
@ApplicationScoped
class ApplicationInfoResource @Inject()(archiveManifest: ApplicationBuildInfo, gitProperties: GitProperties) {
  def this() = this(null, null)

  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  def info: mutable.Map[String, AnyRef] =
    mutable.LinkedHashMap[String, AnyRef](
      "applicationBuildTime" -> archiveManifest.builtTime,
      "gitBuildTime" -> gitProperties.gitCommitTime,
      "gitCommitId" -> gitProperties.gitCommitId,
      "gitCommitTime" -> gitProperties.gitCommitTime,
      "gitCommitMessageShort" -> gitProperties.getCommitMessageShort
    )
}
