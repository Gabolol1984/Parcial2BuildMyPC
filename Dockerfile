# Etapa 1: Compilar el proyecto usando Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos el pom padre y el del microservicio
COPY pom.xml .
COPY msvc-usuario ./msvc-usuario

# Compilamos solo el msvc-usuario
RUN mvn clean package -DskipTests -pl msvc-usuario -am

# Etapa 2: Crear la imagen final ligera
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/msvc-usuario/target/*.jar app.jar

# Exponemos el puerto (Render lo maneja internamente)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]