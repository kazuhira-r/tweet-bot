package org.littlewings.tweetbot.test

import java.io.InputStream

import org.littlewings.tweetbot.resource.Managed

import scala.io.Source

object TestResourceLoader {
  def byClassLoader(clazz: Class[_], resourcePath: String): String = byClassLoader(clazz.getClassLoader, resourcePath)

  def byClassLoader(classLoader: ClassLoader, resourcePath: String): String = {
    val is =
      for (i <- Managed(classLoader.getResourceAsStream(resourcePath)))
        yield byInputStream(i)

    is.acquireAndGet(identity)
  }

  def byClass(clazz: Class[_], resourcePath: String): String = {
    val is =
      for (i <- Managed(clazz.getResourceAsStream(resourcePath)))
        yield byInputStream(i)

    is.acquireAndGet(identity)
  }

  private[test] def byInputStream(is: InputStream): String = {
    val source =
      for (s <- Managed(Source.fromInputStream(is, "UTF-8")))
        yield s.mkString

    source.acquireAndGet(identity)
  }
}
