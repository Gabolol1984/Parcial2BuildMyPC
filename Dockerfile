# Etapa 1: Compilar
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Copiamos SOLO la carpeta del microservicio de usuario
COPY msvc-usuario ./msvc-usuario

# Entramos a la carpeta y compilamos ahí directamente
WORKDIR /app/msvc-usuario
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copiamos el .jar generado
COPY --from=build /app/msvc-usuario/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] "app.jar"]