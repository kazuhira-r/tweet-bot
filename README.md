# tweet-bot [![Build Status](https://travis-ci.org/kazuhira-r/tweet-bot.svg?branch=master)](https://travis-ci.org/kazuhira-r/tweet-bot)
It is a bot application for Twitter that the author personally uses.

To use it, package it in WAR and deploy it, or run standalone with WildFly Swarm.

## Technology Stack

* [Scala](https://www.scala-lang.org/)
* [Java EE (CDI / JAX-RS)](http://www.oracle.com/technetwork/java/javaee/overview/index.html)
* [Apache DeltaSpike (Configuration / ProjectStage / Scheduler)](https://deltaspike.apache.org/)
* [Twitter4j](http://twitter4j.org/)
* [Infinispan (Persistence / CacheStore)](http://infinispan.org/)
* [Jasypt](http://www.jasypt.org/)
* [WildFly Swarm](http://wildfly-swarm.io/)
* [Maven Git Commit Id Plugin](https://github.com/ktoso/maven-git-commit-id-plugin)

*Note: WildFly Swarm is only used for standalone execution.*

## build or execute

### execute (using WildFly Swarm)
```shellscript
$ ./run-development.sh
```

*Note: applied "-Dorg.apache.deltaspike.ProjectStage=Development"*

### execute (without build)
```shellscript
$ ./no-build-run-development.sh
```

*Note: applied "-Dorg.apache.deltaspike.ProjectStage=Development"*

*Note: It must be created by Fat JAR by WildFly Swarm first.*

### packaging WAR (no WildFly Swarm)
```shellscript
$ ./slim-packaging.sh
```

### packaging Fat JAR (with WildFly Swarm)
```shellscript
$ ./swarm-packaging.sh
```

### Unit-Test
```shellscript
$ mvn test
```
