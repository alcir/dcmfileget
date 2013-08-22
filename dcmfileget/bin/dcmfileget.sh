#!/bin/sh
MAIN_CLASS=start
MAIN_JAR=dcmfileget.jar

DIRNAME="`dirname "$0"`"

# Setup SCRIPT_HOME
if [ "x$SCRIPT_HOME" = "x" ]; then
    SCRIPT_HOME=`cd "$DIRNAME"/..; pwd`
fi

# Setup the JVM
if [ "x$JAVA_HOME" != "x" ]; then
    JAVA=$JAVA_HOME/bin/java
else
    JAVA="java"
fi

CP="$SCRIPT_HOME/etc"
CP="$CP:$SCRIPT_HOME/lib/$MAIN_JAR"
CP="$CP:$SCRIPT_HOME/lib/dcm4che-tool-dcmqr-2.0.26.jar"
CP="$CP:$SCRIPT_HOME/lib/dcm4che-core-2.0.26.jar"
CP="$CP:$SCRIPT_HOME/lib/dcm4che-net-2.0.26.jar"
CP="$CP:$SCRIPT_HOME/lib/slf4j-log4j12-1.6.1.jar"
CP="$CP:$SCRIPT_HOME/lib/slf4j-api-1.6.1.jar"
CP="$CP:$SCRIPT_HOME/lib/log4j-1.2.16.jar"
CP="$CP:$SCRIPT_HOME/lib/commons-cli-1.2.jar"
CP="$CP:$SCRIPT_HOME/lib/dcm4che-iod-2.0.26.jar"
CP="$CP:$SCRIPT_HOME/lib/mail.jar"
CP="$CP:$SCRIPT_HOME/lib/commons-collections-3.2.1.jar"
CP="$CP:$SCRIPT_HOME/lib/commons-lang3-3.1.jar"

# Execute the JVM
#echo $JAVA $JAVA_OPTS -cp "$CP" $MAIN_CLASS "$@"
exec $JAVA $JAVA_OPTS -cp "$CP" $MAIN_CLASS "$@"
