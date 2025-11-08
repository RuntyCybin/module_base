#!/bin/bash

# Script de ayuda para desarrollo con Docker Compose
# Uso: ./dev.sh [comando]

case "$1" in
    "build"|"")
        echo "🔨 Reconstruyendo y ejecutando la aplicación..."
        docker-compose up --build
        ;;
    "rebuild")
        echo "🔨 Reconstruyendo sin caché..."
        docker-compose down
        docker-compose build --no-cache
        docker-compose up
        ;;
    "run")
        echo "▶️  Ejecutando la aplicación (sin reconstruir)..."
        docker-compose up
        ;;
    "stop")
        echo "⏹️  Deteniendo contenedores..."
        docker-compose down
        ;;
    "logs")
        echo "📋 Mostrando logs..."
        docker-compose logs -f
        ;;
    "clean")
        echo "🧹 Limpiando todo (contenedores, imágenes, volúmenes)..."
        docker-compose down -v --rmi all
        ;;
    "status")
        echo "📊 Estado de los contenedores:"
        docker-compose ps -a
        ;;
    "shell")
        echo "🐚 Abriendo shell en el contenedor..."
        docker-compose exec hello-world-app /bin/bash || docker-compose run --rm hello-world-app /bin/bash
        ;;
    *)
        echo "Uso: ./dev.sh [comando]"
        echo ""
        echo "Comandos disponibles:"
        echo "  build, (vacío)  - Reconstruye y ejecuta (RECOMENDADO para cambios en código)"
        echo "  rebuild         - Reconstruye sin caché (para cambios en Dockerfile/pom.xml)"
        echo "  run             - Ejecuta sin reconstruir"
        echo "  stop            - Detiene los contenedores"
        echo "  logs            - Muestra los logs"
        echo "  clean           - Limpia todo (contenedores, imágenes, volúmenes)"
        echo "  status          - Muestra el estado de los contenedores"
        echo "  shell           - Abre una shell en el contenedor"
        exit 1
        ;;
esac


