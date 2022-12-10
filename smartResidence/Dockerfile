FROM openjdk:17-alpine
RUN apk --no-cache add curl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
