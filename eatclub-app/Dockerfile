FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/eatclub-app-1.0-SNAPSHOT.jar app.jar

# Expose port 8080 (App Runner expects this)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]