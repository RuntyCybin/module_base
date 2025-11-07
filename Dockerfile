# Usar imagen oficial de Eclipse Temurin (OpenJDK) 21
FROM eclipse-temurin:21-jdk

# Establecer directorio de trabajo
WORKDIR /app

# Instalar Maven 3.9.11
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://archive.apache.org/dist/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz && \
    tar -xzf apache-maven-3.9.11-bin.tar.gz && \
    mv apache-maven-3.9.11 /opt/maven && \
    rm apache-maven-3.9.11-bin.tar.gz && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Configurar variables de entorno para Maven
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY core/pom.xml core/
COPY app/pom.xml app/

# Copiar código fuente
COPY core/src ./core/src
COPY app/src ./app/src

# Verificar versiones
RUN java -version && mvn -version

# Compilar la aplicación (esto compilará todos los módulos)
RUN mvn clean package -DskipTests

# Exponer puerto (opcional, para futuras extensiones)
EXPOSE 8080

# Comando por defecto para ejecutar la aplicación
CMD ["java", "-jar", "app/target/hello-world-app-1.0.0.jar"]