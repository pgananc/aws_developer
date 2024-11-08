#### CREACION DEL JAR ####
FROM maven:3-openjdk-17-slim AS builder

WORKDIR /app
COPY ./pom.xml .
RUN mvn -e -B dependency:go-offline
COPY ./src ./src
RUN mvn -e -B -D maven.test.skip=true package


#### FASE FINAL DE LA IMAGEN ####
FROM openjdk:17-slim

WORKDIR /workspace

ARG USER="app"
ARG GROUP="gapp"
ARG USERUID="1000"
ARG GROUPUID="1000"

RUN addgroup --gid $GROUPUID $GROUP
RUN adduser --uid $USERUID --gid $GROUPUID --disabled-password $USER

USER $USER

COPY --from=builder --chown=$USER:$GROUP /app/target/tx-spring*.jar app.jar

ENTRYPOINT exec java -jar /workspace/app.jar