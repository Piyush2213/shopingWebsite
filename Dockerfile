FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /application

COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

FROM openjdk:17

COPY --from=build /application/target/e-commerce-0.0.1-SNAPSHOT.jar /e-commerce-0.0.1-SNAPSHOT.jar
COPY fashion.csv /

RUN ls /

ENTRYPOINT ["java", "-jar", "/e-commerce-0.0.1-SNAPSHOT.jar"]
