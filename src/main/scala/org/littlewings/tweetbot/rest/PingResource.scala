package org.littlewings.tweetbot.rest

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.{Context, MediaType, UriInfo}
import javax.ws.rs.{GET, Path, Produces}

import org.slf4j.Logger

@Path("ping")
@ApplicationScoped
class PingResource {
  @Inject
  private[rest] var logger: Logger = _

  @GET
  @Produces(Array(MediaType.TEXT_PLAIN))
  def execute(@Context uriInfo: UriInfo): String = {
    logger.info("access url = {}", uriInfo.getRequestUri)
    "Ping OK!"
  }
}
