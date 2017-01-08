#!/bin/bash

./swarm-packaging.sh && java -Dorg.apache.deltaspike.ProjectStage=SystemTest "$@" -jar target/ROOT-swarm.jar
