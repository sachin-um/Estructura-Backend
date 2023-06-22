FROM openjdk:17-jdk

WORKDIR /app

COPY target/API-1.0.0.jar /app/API.jar

EXPOSE 8080

CMD ["java", "-jar", "API.jar"]