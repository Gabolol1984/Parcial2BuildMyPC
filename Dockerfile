# ── Etapa 1: Compilar ────────────────────────────────────────────────────────
FROM maven:3-eclipse-temurin-25 AS build
WORKDIR /app

# Copiamos la carpeta del microservicio
COPY msvc-usuario ./msvc-usuario

# Entramos a la carpeta y compilamos
WORKDIR /app/msvc-usuario
RUN mvn clean package -DskipTests

# ── Etapa 2: Ejecutar ─────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copiamos el .jar generado
COPY --from=build /app/msvc-usuario/target/*.jar app.jar

# Render asigna el puerto mediante la variable de entorno PORT (normalmente 10000 u 8080)
EXPOSE 8080 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
