#!/bin/bash
if [ -f /opt/cm/before.sh ]; then
source /opt/cm/before.sh;
fi
exec java $JAVA_OPTS org.springframework.boot.loader.JarLauncher