FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY backend/target/*.jar PPA.jar
EXPOSE 8080
CMD ["java", "-jar", "PPA.jar"]