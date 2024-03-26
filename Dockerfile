FROM openjdk:21-slim
WORKDIR /app
COPY target/car-management-service-1.0-SNAPSHOT.jar /app/car-management-service.jar
EXPOSE 8080
CMD ["java", "-jar", "car-management-service.jar"]