FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} resource-processor.jar

ENTRYPOINT ["java", "-jar", "/resource-processor.jar"]