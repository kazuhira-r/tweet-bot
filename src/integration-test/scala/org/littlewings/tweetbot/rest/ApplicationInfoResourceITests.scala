package org.littlewings.tweetbot.rest

import java.net.URL
import javax.ws.rs.client.ClientBuilder

import org.jboss.arquillian.container.test.api.RunAsClient
import org.jboss.arquillian.junit.Arquillian
import org.jboss.arquillian.test.api.ArquillianResource
import org.junit.Test
import org.junit.runner.RunWith
import org.littlewings.tweetbot.resource.Managed
import org.littlewings.tweetbot.test.{ArquillianDeploymentSupport, ArquillianTestSupport}

object ApplicationInfoResourceITests extends ArquillianDeploymentSupport

@RunWith(classOf[Arquillian])
class ApplicationInfoResourceITests extends ArquillianTestSupport {
  val resourcePrefix: String = ""

  @ArquillianResource
  var deploymentUrl: URL = _

  @RunAsClient
  @Test
  def applicationBuildInfo(): Unit = {
    for {
      client <- Managed(ClientBuilder.newBuilder.build)
      response <- Managed(client.target(deploymentUrl.toString + resourcePrefix + "info").request.get)
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
