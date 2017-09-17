package org.littlewings.tweetbot.rest

import javax.ws.rs.client.ClientBuilder

import org.junit.Test
import org.littlewings.tweetbot.resource.Managed
import org.littlewings.tweetbot.test.JaxrsServerTestSupport

class ApplicationInfoResourceITests extends JaxrsServerTestSupport {
  @Test
  def applicationBuildInfo(): Unit =
    withServer { server =>
      for {
        client <- Managed(ClientBuilder.newBuilder.build)
        response <- Managed(client.target(server.withUrl("/info")).request.get)
      } {
        val map = response.readEntity(classOf[java.util.Map[String, AnyRef]])
        map.containsKey("applicationBuildTime") should be(true)
        map.containsKey("gitBuildTime") should be(true)
        map.containsKey("gitCommitId") should be(true)
        map.containsKey("gitCommitTime") should be(true)
        map.containsKey("gitCommitMessageShort") should be(true)

        map.containsKey("invalidEntry") should be(false)
      }
    }
}
