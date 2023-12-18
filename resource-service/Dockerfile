FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} resource-service.jar

ENTRYPOINT ["java", "-jar", "/resource-service.jar"]