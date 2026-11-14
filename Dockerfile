# 1. Build stage using Gradle 8+ matching image to avoid redundant downloads.
FROM gradle:8.14.6-jdk17 AS build
WORKDIR /app
COPY . .

# Give gradlew execution permissions (crucial when deploying from Windows to Linux/Render).
RUN chmod +x ./gradlew

# CRUCIAL FOR FREE TIER: Force the Gradle Daemon to stay low-memory and use the wrapper.
RUN ./gradlew clean bootJar -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx300m -XX:+UseSerialGC" && \
    JAR_PATH=$(find build/libs -maxdepth 1 -type f -name '*.jar' ! -name '*-plain.jar' -print -quit) && \
    echo "Found JAR: $JAR_PATH" && \
    test -n "$JAR_PATH" && \
    cp "$JAR_PATH" /app/app.jar

# 2. Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar from the build stage.
COPY --from=build /app/app.jar ./app.jar
ENV SERVER_PORT=8080
EXPOSE 8080

# CRUCIAL FOR FREE TIER: Prevent Spring Boot from bursting past 512MB RAM at startup.
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:+ExitOnOutOfMemoryError", "-Xms64m", "-Xmx300m", "-jar", "/app/app.jar"]