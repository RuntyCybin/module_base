# Hello World - Java 21 con Maven 3.9.11 (Modularizado)

Una aplicación "Hello World" modularizada desarrollada con **JDK 21** y **Maven 3.9.11**, preparada para ejecutarse en Docker usando Docker Compose.

## 🚀 Características

- **JDK 21**: Utiliza las últimas características de Java
- **Maven 3.9.11**: Gestión de dependencias y construcción
- **Arquitectura Modular**: Separación clara de responsabilidades
- **Docker**: Contenedorización completa
- **Docker Compose**: Orquestación de servicios
- **Características modernas de Java**: Text Blocks, Pattern Matching, Switch Expressions

## 📋 Prerrequisitos

- Docker
- Docker Compose
- Git (opcional)

## 🛠️ Estructura del Proyecto

```
oauthtest/
├── pom.xml                          # POM padre
├── core/                            # Módulo core
│   ├── pom.xml
│   └── src/main/java/com/example/core/
│       ├── HelloWorldService.java   # Servicio principal
│       └── SystemUtils.java         # Utilidades del sistema
├── app/                             # Módulo de aplicación
│   ├── pom.xml
│   └── src/main/java/com/example/app/
│       ├── HelloWorldApplication.java  # Aplicación principal
│       └── ApplicationRunner.java      # Ejecutor de la aplicación
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
└── README.md
```

## 🐳 Ejecución con Docker Compose

### 1. Construir y ejecutar la aplicación

```bash
# Construir y ejecutar en un solo comando
docker-compose up --build

# O ejecutar en segundo plano
docker-compose up --build -d
```

### 2. Ver los logs

```bash
# Ver logs en tiempo real
docker-compose logs -f

# Ver logs del servicio específico
docker-compose logs -f hello-world-app
```

### 3. Detener la aplicación

```bash
# Detener los servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v
```

## 🔧 Comandos Útiles

### Desarrollo Local (sin Docker)

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass=com.example.HelloWorld

# Crear JAR ejecutable
mvn clean package

# Ejecutar el JAR
java -jar target/hello-world-1.0.0.jar
```

### Docker (sin Docker Compose)

```bash
# Construir la imagen
docker build -t hello-world:latest .

# Ejecutar el contenedor
docker run --rm hello-world:latest

# Ejecutar con nombre personalizado
docker run --name mi-hello-world --rm hello-world:latest
```

## 📊 Salida Esperada

Cuando ejecutes la aplicación, verás algo similar a:

```
¡Hola Mundo desde Java 21!
Versión de Java: 21.0.1
Vendor: Eclipse Adoptium
OS: Linux 5.15.0-91-generic

--- Características de Java 21 ---
Este es un ejemplo de Text Block
que permite escribir texto multilínea
de forma más legible.

Longitud del string: 30
lunes es un día laboral

¡Aplicación ejecutada exitosamente!
```

## 🐛 Solución de Problemas

### Error de memoria
Si encuentras problemas de memoria, ajusta las variables de entorno en `docker-compose.yml`:

```yaml
environment:
  - JAVA_OPTS=-Xmx1g -Xms512m
```

### Limpiar Docker
```bash
# Eliminar contenedores parados
docker container prune

# Eliminar imágenes no utilizadas
docker image prune

# Limpieza completa (¡cuidado!)
docker system prune -a
```

## 🔄 Modo Desarrollo

Para desarrollo activo, puedes modificar el `docker-compose.yml` para montar el código fuente:

```yaml
volumes:
  - ./src:/app/src
  - ./pom.xml:/app/pom.xml
```

## 📝 Notas

- La aplicación se ejecuta una vez y termina (no es un servidor web)
- El contenedor se detiene automáticamente después de mostrar la salida
- Para aplicaciones web persistentes, necesitarías modificar la clase principal

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Por favor, abre un issue o pull request.

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.
