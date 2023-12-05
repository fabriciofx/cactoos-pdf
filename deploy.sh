#!/bin/bash
export JDK_JAVA_OPTIONS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED"
mvn -Pqulice -Psonatype -Pcactoos-pdf clean deploy -DskipTests=true --settings=../settings.xml
