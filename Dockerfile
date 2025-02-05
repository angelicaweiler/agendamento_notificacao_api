FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/agendamento-notificacao-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

