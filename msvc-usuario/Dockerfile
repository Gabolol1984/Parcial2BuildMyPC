# ── Etapa 1: Compilar ────────────────────────────────────────────────────────
FROM maven:3-eclipse-temurin-25 AS build
WORKDIR /app

# Copiamos los archivos del proyecto (contexto msvc-usuario)
COPY pom.xml .
COPY src ./src

# Compilamos saltando tests
RUN mvn clean package -DskipTests

# ── Etapa 2: Ejecutar ─────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copiamos el .jar generado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
