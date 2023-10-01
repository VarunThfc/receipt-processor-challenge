#FROM openjdk:8-jre-alpine
FROM maven:3.6.0-jdk-11-slim
WORKDIR /app

# Cache dependencies to prevent re-download on src changes
COPY ./pom.xml /app
RUN mvn dependency:go-offline

# Build/package application
COPY ./src /app/src
RUN mvn clean package -DskipTests



#
# Package stage
#
#FROM openjdk:8-jre-slim
#COPY --from=build /app/target/receipt-processor-0.0.1.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/target/receipt-processor-0.0.1-SNAPSHOT.jar"]