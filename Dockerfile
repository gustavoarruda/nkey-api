FROM openjdk:8-jdk
WORKDIR /opt/dgfit/api
COPY target/api-*-SNAPSHOT.jar api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "nkey-api.jar"]