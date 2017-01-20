package org.littlewings.tweetbot.rest

import javax.ws.rs.core.MediaType
import javax.ws.rs.{GET, Path, Produces}

@Path("/")
class TopResource {
  @GET
  @Produces(Array(MediaType.TEXT_PLAIN))
  def get: String = "This is tweet bot application"
}
