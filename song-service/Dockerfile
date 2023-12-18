# Use an official OpenJDK runtime as a parent image
FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} song-service.jar

# Define the command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "/song-service.jar"]