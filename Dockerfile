# Etapa de construcción
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Informar el puerto donde se ejecuta  el contenedor
EXPOSE 8080

# Directorio de trabajo dentro del contenedor
WORKDIR /root

# Copiar archivos necesarios para aprovechar caché
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

RUN chmod +x ./mvnw
# Descargar las dependencias
RUN ./mvnw dependency:resolve -Dmaven.wagon.http.retryHandler.count=3

# Copiar codigo fuente dentro del contenedor
COPY ./src /root/src

# Construir nuestra aplicación
RUN ./mvnw clean install -DskipTests

# Levantar nuestra aplicacion cuando el contenedor inicie
ENTRYPOINT ["java","-jar","/root/target/pasteleria-0.0.1-SNAPSHOT.jar"]