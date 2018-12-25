#
# Build the app using maven in a container
#
# FROM maven:3-jdk-8-alpine AS devenv
# RUN git clone https://github.com/banq/jivejdon.git
# # Create app directory
# WORKDIR /jivejdon
# RUN mvn package

# #
# #mysql
# #
FROM mysql:5.7 

# Copy the database initialize script: 
# Contents of /docker-entrypoint-initdb.d are run on mysqld startup
ADD  docker/mysql/ /docker-entrypoint-initdb.d/

# Default values for passwords and database name. Can be overridden on docker run
ENV MYSQL_ROOT_PASSWORD=root 
ENV MYSQL_DATABASE=jivejdon
ENV MYSQL_USER=test
ENV MYSQL_PASSWORD=test

RUN /etc/init.d/mysql start &

# #
# # deploy application to tomcat

# FROM tomcat:8.5-jre8-alpine

# ADD docker/tomcat/context.xml /usr/local/tomcat/conf/
# ADD docker/tomcat/server.xml /usr/local/tomcat/conf/


# # # add MySQL JDBC driver jar
# ADD docker/tomcat/mysql-connector-java-5.1.36-bin.jar /usr/local/tomcat/lib/
# ADD target/ROOT.war /usr/local/tomcat/webapps/
# # # create mount point for volume with application
# WORKDIR /usr/local/tomcat/webapps/


# # # add tomcat jpda debugging environmental variables
# ENV JPDA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
# ENV JPDA_ADDRESS="8000"
# ENV JPDA_TRANSPORT="dt_socket"

# # # start tomcat with remote debugging
#  EXPOSE 8080

#  WORKDIR /usr/local/tomcat/bin
#  RUN ls -l
#  RUN JAVA_OPTS="-XX:+UseStringCache   -Djava.security.auth.login.config=../conf/jaas.config"
#  RUN catalina.sh jpda run &

