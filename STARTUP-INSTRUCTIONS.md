# Arranque con Docker - instrucciones

Archivos relevantes:
- `docker-compose.yml`
- `.env`

Servicios y puertos principales:
- `db` (MySQL): `3306`
- `app` (Spring Boot): `8082`
- `gateway` (Spring Cloud Gateway): `8081`
- `eureka` (Eureka Server): `8761`

Comandos rapidos:

```bash
docker-compose up --build -d
docker-compose ps
docker-compose logs --follow --tail 200 app
docker-compose logs --follow --tail 200 gateway
docker-compose logs --follow --tail 200 eureka
docker-compose down -v
```

Verificaciones utiles:

```bash
curl http://localhost:8761/
curl http://localhost:8761/eureka/apps
curl http://localhost:8081/api/productos
curl http://localhost:8081/v3/api-docs
```
