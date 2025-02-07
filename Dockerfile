FROM maven:3.8.4-openjdk-17 as builder
LABEL authors="satish"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/minesweeperapi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.devtools.restart.enabled=true", "/app.jar"]