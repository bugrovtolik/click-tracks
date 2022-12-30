FROM eclipse-temurin:11-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/click-tracks-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]