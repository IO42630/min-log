#!/bin/bash
version="0.1.1"
packaging="jar"
file="target/min-log-${version}.${packaging}"
groupId="com.olexyn.min.log"
artifactId="min-log"

mvn package
mvn install:install-file -Dfile=${file} -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dpackaging=${packaging} -DgeneratePom=true
