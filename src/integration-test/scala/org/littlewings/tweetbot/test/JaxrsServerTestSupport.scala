package org.littlewings.tweetbot.test

import java.io.IOException
import java.lang.annotation.Annotation
import java.net.{InetAddress, ServerSocket}
import javax.enterprise.inject.spi.CDI
import javax.ws.rs.ext.Provider
import javax.ws.rs.{ApplicationPath, Path}

import io.undertow.server.HttpHandler
import io.undertow.servlet.Servlets
import io.undertow.servlet.api.ServletContainerInitializerInfo
import io.undertow.servlet.util.DefaultClassIntrospector
import io.undertow.{Handlers, Undertow}
import org.jboss.resteasy.cdi.CdiInjectorFactory
import org.jboss.resteasy.plugins.servlet.ResteasyServletInitializer
import org.jboss.weld.environment.servlet.Listener
import org.littlewings.tweetbot.resource.Managed
import org.reflections.Reflections

object JaxrsServerTestSupport {
  val DefaultHost: String = "localhost"
  val DefaultStartPort: Int = 8080
  val DefaultPortRange: Int = 100
  val DefaultContextPath: String = ""
}

class JaxrsServerTestSupport extends ScalaTestJUnitTestSupport {
  protected def withServer(fun: JaxrsServer => Unit): Unit =
    withServer(JaxrsServerTestSupport.DefaultHost, JaxrsServerTestSupport.DefaultStartPort, JaxrsServerTestSupport.DefaultContextPath)(fun)

  protected def withServer(startPort: Int, contextPath: String)(fun: JaxrsServer => Unit): Unit =
    withServer(JaxrsServerTestSupport.DefaultHost, startPort, contextPath)(fun)

  protected def withServer(startPort: Int)(fun: JaxrsServer => Unit): Unit =
    withServer(JaxrsServerTestSupport.DefaultHost, startPort, JaxrsServerTestSupport.DefaultContextPath)(fun)

  protected def withServer(host: String, startPort: Int, contextPath: String)(fun: JaxrsServer => Unit): Unit = {
    val port = findPort(host, startPort, JaxrsServerTestSupport.DefaultPortRange)

    val deployment =
      Servlets
        .deployment
        .setClassLoader(getClass.getClassLoader)
        .setContextPath(contextPath)
        .setDeploymentName("tweet-bot-test-jaxrs-server")
        .addInitParameter("resteasy.injector.factory", classOf[CdiInjectorFactory].getName)
        .addServletContainerInitalizer(new ServletContainerInitializerInfo(classOf[ResteasyServletInitializer],
          DefaultClassIntrospector.INSTANCE.createInstanceFactory(classOf[ResteasyServletInitializer]),
          findJaxrsClasses))
        .addListener(Servlets.listener(classOf[Listener]))

    val manager = Servlets.defaultContainer.addDeployment(deployment)
    manager.deploy()

    val serverHandler = manager.start()
    val handler: HttpHandler =
      if (contextPath.isEmpty) serverHandler
      else Handlers.path.addPrefixPath(contextPath, serverHandler)

    val server =
      Undertow
        .builder
        .addHttpListener(port, host)
        .setHandler(handler)
        .build

    try {
      server.start()

      fun(JaxrsServer(host, port, contextPath))
    } finally {
      server.stop()
    }
  }

  protected def injectedBean[T <: AnyRef](targetClass: Class[T], annotations: Array[Annotation] = Array.empty): T =
    CDI.current.select[T](targetClass, annotations: _*).get

  protected def findJaxrsClasses: java.util.Set[Class[_]] = {
    val classes = new java.util.HashSet[Class[_]]

    val packages = Array(
      "org.littlewings"
    )

    packages.foreach { p =>
      val reflections = new Reflections(p)
      classes.addAll(reflections.getTypesAnnotatedWith(classOf[ApplicationPath]))
      classes.addAll(reflections.getTypesAnnotatedWith(classOf[Path]))
      classes.addAll(reflections.getTypesAnnotatedWith(classOf[Provider]))
    }

    classes
  }

  protected def findPort(host: String, startPort: Int, portRange: Int): Int = {
    def testPort(port: Int): Boolean =
      try {
        Managed(new ServerSocket(port, 50, InetAddress.getByName(host))).foreach(_.setReuseAddress(true))
        true
      } catch {
        case e: IOException => false
      }

    (startPort to (startPort + portRange)).collectFirst { case p if testPort(p) => p }.get
  }
}
