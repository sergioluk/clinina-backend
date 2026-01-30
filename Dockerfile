# ===== BUILD =====
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests




FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
