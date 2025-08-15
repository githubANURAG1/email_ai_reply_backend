# ---------- Stage 1: Build ----------
    FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven config first for better caching
COPY pom.xml .
COPY src ./src

# Build the app (skip tests for faster builds)
RUN mvn clean package

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:17

WORKDIR /app

# Copy only the jar from the build stage
COPY --from=build /app/target/email_ai_reply-0.0.1-SNAPSHOT.jar /app/email_ai_reply.jar

# Run the app
ENTRYPOINT ["java", "-jar", "email_ai_reply.jar"]

# Optional: expose the port your app runs on (default: 8080)
EXPOSE 8080