package org.littlewings.tweetbot.test

import java.io.File

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.StringAsset
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven
import org.littlewings.tweetbot.{LoggerSupport, ProductionStage, ProjectStageSupport, TestStage}

trait ArquillianDeploymentSupport {
  @Deployment
  def createBasicDeployment: WebArchive = {
    val webArchive =
      ShrinkWrap
        .create(classOf[WebArchive])
        .addClass(classOf[LoggerSupport])
        .addClass(classOf[ProjectStageSupport])
        .addClass(classOf[ProductionStage])
        .addClass(classOf[TestStage])
        .addPackages(true, "org.littlewings.tweetbot.cachestore")
        .addPackages(true, "org.littlewings.tweetbot.config")
        .addPackages(true, "org.littlewings.tweetbot.producer")
        .addPackages(true, "org.littlewings.tweetbot.resource")
        .addPackages(true, "org.littlewings.tweetbot.standard")
        .addPackages(true, "org.littlewings.tweetbot.tweet")
        .addAsResource(new File("src/main/resources"), "")
        .addAsResource(new StringAsset("org.apache.deltaspike.ProjectStage=Development"), "META-INF/apache-deltaspike.properties")
        .addAsLibraries(
          Maven
            .resolver
            .loadPomFromFile("pom.xml")
            .importRuntimeDependencies
            .resolve("org.scalatest:scalatest_2.12:3.0.1")
            .withTransitivity
            .asFile: _*
        )

    customizeArchive(webArchive)

    webArchive
  }

  protected def customizeArchive(webArchive: WebArchive): Unit = ()
}
