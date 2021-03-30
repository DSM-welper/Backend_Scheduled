FROM openjdk:11.0.9-slim

COPY ./build/libs/*.jar welper_scheduled.jar
ENTRYPOINT ["java", "-jar","/welper_scheduled.jar"]
