FROM openjdk:8-jdk-alpine

MAINTAINER shindongsun0@naver.com

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]