FROM openjdk:8-jdk-alpine

MAINTAINER shindongsun0@naver.com

VOLUME /tmp

EXPOSE 8000

#Run the jar file
ARG JAR_FILE=*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

