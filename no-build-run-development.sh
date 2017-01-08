#!/bin/bash

java -Dorg.apache.deltaspike.ProjectStage=Development "$@" -jar target/ROOT-swarm.jar
