# Use Eclipse Temurin 21 JRE on Alpine (Lightweight and Modern)
FROM eclipse-temurin:21-jre-alpine

# Simply the artifact path
ARG artifact=target/contacts360-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${artifact} app.jar

# This should not be changed
ENTRYPOINT ["java","-jar","app.jar"]
