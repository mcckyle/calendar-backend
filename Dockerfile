# 1. Build stage using Gradle 8+ matching image to avoid redundant downloads.
FROM gradle:8.14.5-jdk17 AS build
WORKDIR /app
COPY . .

# CRUCIAL FOR FREE TIER: Force the Gradle Daemon to stay low-memory and use the wrapper.
RUN ./gradlew clean build -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx300m -XX:+UseSerialGC"

# 2. Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080

# CRUCIAL FOR FREE TIER: Prevent Spring Boot from bursting past 512MB RAM at startup.
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-Xmx300m", "-jar", "app.jar"]