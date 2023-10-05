FROM maven:3.8.4-openjdk-17-slim AS build
LABEL stage=build
WORKDIR /app
COPY ./pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package
FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/target/gift-certificate-system-0.0.1-SNAPSHOT.war app.jar
CMD ["java", "-jar", "app.jar"]
