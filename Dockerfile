FROM eclipse-temurin:17-jre-alpine

# Define el directorio de trabajo para que los comandos se ejecuten desde ahí.
WORKDIR /app

# Copia el JAR en el contenedor.
COPY build/libs/*.jar app.jar

# Exponer el puerto de la aplicación.
EXPOSE 8080

# Define el comando para ejecutar la aplicación.
CMD ["java", "-jar", "app.jar"]