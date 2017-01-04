package org.littlewings.tweetbot

import org.wildfly.swarm.Swarm

object App {
  def main(args: Array[String]): Unit = {
    System.setProperty("org.apache.deltaspike.ProjectStage", "Development")
    Swarm.main(args: _*)
  }
}
