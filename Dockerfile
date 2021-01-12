FROM openjdk:8-jdk
WORKDIR /opt/nkey-api
COPY target/nkey-api-*-SNAPSHOT.jar nkey-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "nkey-api.jar"]