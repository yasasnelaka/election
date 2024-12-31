FROM maven:3-eclipse-temurin-17 AS build



COPY . .


# Run Maven to build the project and package it
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK 17 image for the runtime
FROM eclipse-temurin:17-alpine

# Copy the JAR file from the build stage
COPY --from=build /target/*.jar demo.jar

# Expose the port the application runs on (update if different)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
