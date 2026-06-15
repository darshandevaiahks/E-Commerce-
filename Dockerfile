# Stage 1: Build the WAR using Maven
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Deploy on Tomcat 10.1
FROM tomcat:10.1-jdk17
COPY --from=builder /app/target/AmesingStore.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
