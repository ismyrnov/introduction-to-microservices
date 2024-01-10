FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} song-service.jar

ENTRYPOINT ["java", "-jar", "/song-service.jar"]