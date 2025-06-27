FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean compile package 
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
COPY --from=build /app/target/javaweb-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "app.jar"] 