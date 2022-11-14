#!/bin/sh

home_dir=`pwd`
base_dir=$(dirname $0)/..
cd $base_dir
base_dir=`pwd`
cd $home_dir

if [ ! -d "$base_dir/target/lib" ]; then
  echo "Unable to find $base_dir/lib, which is required to run."
  exit 1
fi

if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

CLASSPATH=$base_dir/config

for jar in $base_dir/target/lib/*.jar; do
    CLASSPATH=$CLASSPATH:$jar
done

for jar in $base_dir/target/*.jar; do
    CLASSPATH=$CLASSPATH:$jar
done

#Log4j2 Async Logging
JAVA_OPTS="$JAVA_OPTS -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"

#Log4j Configuration File
JAVA_OPTS="$JAVA_OPTS -Dlog4j.configurationFile=file://$base_dir/config/log4j2.xml"

#CSV Schema Settings
JAVA_OPTS="$JAVA_OPTS -Xms256m -Xmx1g"
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:+UseStringDeduplication -XX:G1HeapRegionSize=32M -XX:+ExplicitGCInvokesConcurrent -XX:+HeapDumpOnOutOfMemoryError -XX:NumberOfGCLogFiles=20 -XX:+UseGCLogFileRotation -XX:GCLogFileSize=200m -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+PrintGCCause -XX:+PrintTenuringDistribution -XX:+PrintReferenceGC -XX:+PrintAdaptiveSizePolicy -Xloggc:gc.log"


$JAVA -ea $JAVA_OPTS -cp $CLASSPATH \
        com.Application