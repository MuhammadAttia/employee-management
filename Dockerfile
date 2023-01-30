FROM openjdk
ARG VERSION=1.0.0
LABEL version=${VERSION}
LABEL maintainer="Muhammad Attia"
LABEL description="Employee Managment Service"
WORKDIR /usr/local/app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
CMD ["java" ,"-jar", "application.jar"]
