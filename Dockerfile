# 1. Build stage using Gradle.
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# 2. Runtime stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]