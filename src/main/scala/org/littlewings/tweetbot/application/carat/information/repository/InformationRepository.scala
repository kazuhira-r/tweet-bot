package org.littlewings.tweetbot.application.carat.information.repository

import java.util.UUID
import java.util.stream.Collectors
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Typed
import javax.inject.Inject

import org.infinispan.Cache
import org.littlewings.tweetbot.application.carat.information.Information
import org.littlewings.tweetbot.application.carat.information.qualifier.CaratInformationTweetBot
import org.littlewings.tweetbot.resource.Managed

import scala.collection.JavaConverters._

trait InformationRepository {
  def findAll: Seq[Information]

  def saveAll(informations: Seq[Information]): Unit

  def clear(): Unit
}

@Typed(Array(classOf[InformationRepository]))
@ApplicationScoped
class DefaultInformationRepository @Inject()(@CaratInformationTweetBot protected[repository] val cache: Cache[String, Information])
  extends InformationRepository {
  override def findAll: Seq[Information] = {
    val stream =
      for (s <- Managed(cache.values.stream))
        yield s.collect(Collectors.toList[Information]).asScala.toVector
    stream.acquireAndGet(identity)
  }

  override def saveAll(informations: Seq[Information]): Unit = {
    cache.clear()
    informations.foreach {
      information =>
        cache.put(UUID.randomUUID.toString, information)
    }
  }

  override def clear(): Unit = cache.clear()
}
