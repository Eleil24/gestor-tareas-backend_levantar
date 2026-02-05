# --- Etapa 1: Compilar el proyecto con Maven ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Compila el proyecto y salta los tests para ir más rápido
RUN mvn clean package -DskipTests

# --- Etapa 2: Crear la imagen para ejecutarlo ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copia el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto 8080 (el de Spring Boot)
EXPOSE 8080

# Comando para iniciar la app
ENTRYPOINT ["java", "-jar", "app.jar"]