FROM openjdk:16-alpine
ADD target/organisation-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar organisation-0.0.1-SNAPSHOT.jar
