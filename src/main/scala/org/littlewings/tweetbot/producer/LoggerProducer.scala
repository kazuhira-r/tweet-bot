package org.littlewings.tweetbot.producer

import javax.enterprise.context.Dependent
import javax.enterprise.inject.Produces
import javax.enterprise.inject.spi.InjectionPoint

import org.slf4j.{Logger, LoggerFactory}

@Dependent
class LoggerProducer {
  @Produces
  def logger(injectionPoint: InjectionPoint): Logger = {
    val clazz = injectionPoint.getMember.getDeclaringClass

    if (clazz.getName.contains("$" + "Proxy" + "$"))
      LoggerFactory.getLogger(clazz.getSuperclass)
    else
      LoggerFactory.getLogger(clazz)
  }
}
