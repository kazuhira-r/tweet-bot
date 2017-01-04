package org.littlewings.tweetbot

import org.apache.deltaspike.core.api.exclude.Exclude
import org.apache.deltaspike.core.api.projectstage.ProjectStage.{Development, UnitTest}

trait ProjectStageSupport

@Exclude(ifProjectStage = Array(classOf[Development], classOf[UnitTest]))
abstract class ProductionStage extends ProjectStageSupport

@Exclude(exceptIfProjectStage = Array(classOf[Development], classOf[UnitTest]))
abstract class TestStage extends ProjectStageSupport
