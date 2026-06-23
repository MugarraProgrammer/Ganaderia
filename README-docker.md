# Docker - instrucciones rapidas

Requisitos: Docker y Docker Compose instalados.

Construir y arrancar servicios:

```bash
docker-compose up --build -d
```

Esto levanta:
- `db` con MySQL 8
- `eureka` en `8761`
- `app` en `8082`
- `gateway` en `8081`

Variables importantes:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_PROFILES_ACTIVE`
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`

Parar y eliminar contenedores y volumenes:

```bash
docker-compose down -v
```

Comprobaciones rapidas:

```bash
curl http://localhost:8761
curl http://localhost:8081/api/productos
curl http://localhost:8081/v3/api-docs
```
