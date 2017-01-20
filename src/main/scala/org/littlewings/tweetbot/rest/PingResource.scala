package org.littlewings.tweetbot.rest

import javax.ws.rs.core.{Context, MediaType, UriInfo}
import javax.ws.rs.{GET, Path, Produces}

import org.littlewings.tweetbot.LoggerSupport

@Path("ping")
class PingResource extends LoggerSupport {
  @GET
  @Produces(Array(MediaType.TEXT_PLAIN))
  def execute(@Context uriInfo: UriInfo): String = {
    logger.info("access url = {}", uriInfo.getRequestUri)
    "Ping OK!"
  }
}
