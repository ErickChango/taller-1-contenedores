# ==========================================
# Dockerfile para Spring Boot
# ==========================================
# Este Dockerfile crea una imagen optimizada para aplicaciones Spring Boot
# Utiliza build multi-etapa para reducir el tamaño final de la imagen

# ==========================================
# ETAPA 1: Construcción (Build Stage)
# ==========================================
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivo de configuración de Maven
COPY pom.xml .

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
# -DskipTests omite las pruebas para acelerar el build
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: Producción (Production Stage)
# ==========================================
FROM eclipse-temurin:17-jre-alpine

# Establecer directorio de trabajo
WORKDIR /app

# Crear usuario no-root para ejecutar la aplicación (seguridad)
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Cambiar propietario del archivo
RUN chown spring:spring app.jar

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto de la aplicación Spring Boot
EXPOSE 8080

# Variables de entorno opcionales
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# ==========================================
# Instrucciones de uso:
# ==========================================
# 1. Construir la imagen:
#    docker build -t spring-boot-app:1.0 .
#
# 2. Ejecutar el contenedor:
#    docker run -d -p 8080:8080 --name spring-container spring-boot-app:1.0
#
# 3. Con variables de entorno:
#    docker run -d -p 8080:8080 \
#      -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydb \
#      -e SPRING_DATASOURCE_USERNAME=user \
#      -e SPRING_DATASOURCE_PASSWORD=pass \
#      --name spring-container spring-boot-app:1.0
#
# 4. Acceder a la aplicación:
#    http://localhost:8080
# ==========================================
