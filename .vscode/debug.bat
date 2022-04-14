set JAVA_OPTS=-XX:+UseStringCache   -Djava.security.auth.login.config=../conf/jaas.config 

rem -XX:+UseCompressedStrings   -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=8086 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

rem set JAVA_OPTS= %JAVA_OPTS% -XX:+UseAdaptiveGCBoundary -XX:MaxGCPauseMillis=150 -XX:+UseAdaptiveSizePolicy -XX:+DisableExplicitGC -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=90 -XX:+UseCMSInitiatingOccupancyOnly -XX:+CMSClassUnloadingEnabled -XX:+PerfDisableSharedMem


catalina.bat jpda start