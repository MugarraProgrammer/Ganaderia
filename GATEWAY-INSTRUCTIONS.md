# Spring Cloud Gateway - instrucciones

Resumen:
- Modulo `gateway` en el repo.
- Expone puerto `8081`.
- Actua como punto de entrada central.
- Resuelve servicios mediante Eureka usando `lb://ganaderia`.

Requisitos:
- `eureka` debe estar disponible antes de arrancar el `gateway`.

Comprobar estado:

```bash
docker-compose ps
docker-compose logs --tail 200 gateway
docker-compose logs --tail 200 eureka
```

Rutas principales:
- `/api/**`
- `/swagger-ui/**`
- `/v3/api-docs/**`

Pruebas rapidas:

```bash
curl http://localhost:8081/api/productos
curl http://localhost:8081/v3/api-docs
curl -I http://localhost:8081/swagger-ui.html
```
