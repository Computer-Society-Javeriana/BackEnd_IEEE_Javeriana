
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

RUN cd backend && mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/backend/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
