FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src
RUN ./mvnw package

FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/doctoranywhere.jar ./doctoranywhere.jar
ENTRYPOINT ["java", "-jar", "doctoranywhere.jar"]