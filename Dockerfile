# Use lightweight OpenJDK 17 base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file from target folder
COPY target/codeconvert-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will map it automatically)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
