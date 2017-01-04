package org.littlewings.tweetbot.job

import javax.enterprise.inject.spi.{Bean, CDI}
import javax.servlet.annotation.WebListener
import javax.servlet.{ServletContextEvent, ServletContextListener}

import org.apache.deltaspike.scheduler.spi.Scheduler
import org.littlewings.tweetbot.LoggerSupport
import org.quartz.Job

import scala.collection.JavaConverters._

@WebListener
class JobActivator extends ServletContextListener with LoggerSupport {
  override def contextInitialized(sce: ServletContextEvent): Unit = {
    val scheduler = CDI.current.select(classOf[Scheduler[Job]]).get

    val beanManager = CDI.current.getBeanManager
    val jobBeans =
      beanManager
        .getBeans(classOf[Job])
        .asInstanceOf[java.util.Set[Bean[_ <: Job]]]
        .asScala

    logger.info("found JobBeans size = {}", jobBeans.size)

    jobBeans.foreach { jobBean =>
      logger.info("register JobClass = {}", jobBean)
      scheduler.registerNewJob(jobBean.getBeanClass.asInstanceOf[Class[Job]])
    }
  }

  override def contextDestroyed(sce: ServletContextEvent): Unit = ()
}
