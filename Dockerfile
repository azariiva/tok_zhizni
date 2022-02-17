FROM openjdk:11-oracle
EXPOSE 8080
ARG JAR_FILE=build/libs/tok_zhizni-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
