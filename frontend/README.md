# Frontend - Tienda Online

Interfaz ligera construida únicamente con HTML, CSS y JavaScript nativo.
Consume los endpoints del backend mediante `fetch` asíncrono y muestra el inventario
en una tabla con filtros rápidos.

## Estructura

- `public/index.html` – maqueta principal y contenedores de datos.
- `public/styles.css` – estilos responsivos con tipografía Inter.
- `public/app.js` – lógica nativa para consultar `/articles`, filtrar y pintar la tabla.
- `Dockerfile` – imagen mínima basada en `nginx:alpine`.
- `nginx.conf` – proxy opcional a la API usando el prefijo `/api`.

## Desarrollo rápido

Puedes abrir el frontend sin dependencias adicionales:

```bash
cd frontend/public
npx serve .
```

El cliente intentará conectar con `http://localhost:8080/articles`. Si se sirve detrás
de Nginx, usará el proxy `/api/articles`.

## Docker

`docker-compose` construye y publica el frontend en `http://localhost:4200`.
El contenedor utiliza Nginx para archivos estáticos y reenvía `/api/*` al servicio
`tienda-online-app`.
