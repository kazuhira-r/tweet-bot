package org.littlewings.tweetbot.test

case class JaxrsServer(host: String, port: Int, contextPath: String) {
  def baseUrl: String = s"http://$host:$port$contextPath"

  def withUrl(relativePath: String): String = s"$baseUrl$relativePath"
}
