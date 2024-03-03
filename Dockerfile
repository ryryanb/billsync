# Use the official OpenJDK base image
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/billsync-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the Spring Boot app will run on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
