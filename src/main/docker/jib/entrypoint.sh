#!/bin/sh

echo "The application will start in ${FLETA_SLEEP}s..." && sleep ${FLETA_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.fleta.watchingservice.WatchingServiceApp"  "$@"
