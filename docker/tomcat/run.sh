#!/bin/sh
exec JAVA_OPTS="-XX:+UseStringCache   -Djava.security.auth.login.config=../conf/jaas.config" 
exec ${CATALINA_HOME}/bin/catalina.sh jpda run