package org.littlewings.tweetbot.application.carat.information.repository

import java.util.UUID
import java.util.stream.Collectors
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

import org.infinispan.Cache
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.qualifier.CaratInformationTweetBot
import org.littlewings.tweetbot.resource.Managed

import scala.collection.JavaConverters._

@ApplicationScoped
class InformationRepository {
  @CaratInformationTweetBot
  @Inject
  private[repository] var cache: Cache[String, Information] = _

  def findAll: Seq[Information] = {
    val stream =
      for (s <- Managed(cache.values.stream))
        yield s.collect(Collectors.toList[Information]).asScala.toVector
    stream.acquireAndGet(identity)
  }

  def saveAll(informations: Seq[Information]): Unit = {
    cache.clear()
    informations.foreach { information =>
      cache.put(UUID.randomUUID.toString, information)
    }
  }

  def clear(): Unit = cache.clear()
}
