FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/target/gift-certificate-system-0.0.1-SNAPSHOT.war app.jar
CMD ["java", "-jar", "app.jar"]
