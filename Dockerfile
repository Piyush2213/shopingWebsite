FROM openjdk:17

COPY target/e-commerce-0.0.1-SNAPSHOT.jar /e-commerce-0.0.1-SNAPSHOT.jar
COPY fashion.csv /
RUN ls /
ENTRYPOINT ["java", "-jar", "/e-commerce-0.0.1-SNAPSHOT.jar"]