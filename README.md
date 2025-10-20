
# Franchise Challenge — Backend Developer (Accenture Technical Test)

## Descripción General

Este proyecto implementa una **API REST reactiva** para la gestión de franquicias, sucursales y productos.  
Está desarrollado con **Spring Boot + WebFlux**, estructurado con **Clean Architecture**, contiene **tests unitarios** con alta cobertura, y está **contenedorizado con Docker**.

El propósito de este proyecto es demostrar la aplicación de principios de arquitectura limpia, desarrollo reactivo y buenas prácticas de ingeniería backend utilizando herramientas modernas del ecosistema Java.

---

## Objetivos Técnicos

-  Arquitectura Limpia (**Clean Architecture / Hexagonal**)
-  Programación Reactiva (**Spring WebFlux + Reactor**)
-  Pruebas unitarias con alta cobertura (**JUnit 5, Mockito, StepVerifier**)
-  Contenedorización con **Dockerfile** y **Docker Compose**
-  Documentación de API con **Swagger (SpringDoc OpenAPI 3)**

---

##  Estructura del Proyecto

```
franchise-challenge/
├── build.gradle
├── settings.gradle
├── docker-compose.yml
├── Dockerfile
├── README.md
└── src/
    ├── main/
    │   ├── java/com/franchise/challenge/
    │   │   ├── application/         # Configuración de casos de uso (UseCasesConfig)
    │   │   ├── domain/              # Entidades, puertos y casos de uso
    │   │   ├── infrastructure/
    │   │   │   ├── adapter/         # Persistencia con MongoDB Reactive
    │   │   │   ├── entrypoint/      # Handlers + Routers (API reactiva)
    │   │   │   ├── config/          # Swagger, validaciones
    │   │   │   └── util/            # Constantes globales
    │   └── resources/
    │       └── application.properties
    └── test/                        # Pruebas unitarias
```

---

##  Ejecución del Proyecto

### 🔹 Requisitos Previos
- **Java 21+**
- **Gradle 8+**
- **Docker Desktop**

### 🔹 Ejecución con Docker Compose
El proyecto incluye un `docker-compose.yml` que levanta la aplicación y una instancia de MongoDB.

```bash
docker compose up --build
```

Esto ejecuta:
- `mongo:6.0` → en `localhost:27017`
- `challenge-app` → en `localhost:8080`

> Si el puerto 27017 está ocupado, detén cualquier otra instancia de MongoDB local antes de ejecutar el comando.

---

## Pruebas Unitarias

Para ejecutar las pruebas y generar el reporte de cobertura:

```bash
./gradlew clean test jacocoTestReport
```

El reporte de cobertura se genera en:
```
build/reports/jacoco/test/html/index.html
```

Incluye pruebas para:
- `FranchiseUseCase` (lógica de negocio)
- `FranchiseAdapter` (persistencia)
- `FranchiseHandler` (API Reactiva)

---

##  Endpoints Principales

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `POST` | `/api/v1/franchises` | Crear una franquicia |
| `PUT` | `/api/v1/franchises/{franchiseId}/rename` | Renombrar una franquicia |
| `POST` | `/api/v1/franchises/{franchiseId}/branches` | Agregar una sucursal |
| `PUT` | `/api/v1/franchises/{franchiseId}/branches/{branchName}/rename` | Renombrar una sucursal |
| `POST` | `/api/v1/franchises/{franchiseId}/branches/{branchName}/products` | Agregar un producto |
| `PUT` | `/api/v1/franchises/{franchiseId}/branches/{branchName}/products/{productName}/rename` | Renombrar un producto |
| `PUT` | `/api/v1/franchises/{franchiseId}/branches/{branchName}/products/{productName}/stock` | Actualizar stock de un producto |
| `DELETE` | `/api/v1/franchises/{franchiseId}/branches/{branchName}/products/{productName}` | Eliminar un producto |
| `GET` | `/api/v1/franchises/{franchiseId}/max-stock` | Obtener producto con mayor stock |

---

##  Ejemplos de Pruebas con cURL

```bash
# Crear una franquicia
curl -X POST http://localhost:8080/api/v1/franchises -H "Content-Type: application/json" -d '{"name":"TechZone"}'

# Agregar una sucursal
curl -X POST http://localhost:8080/api/v1/franchises/{id}/branches -H "Content-Type: application/json" -d '{"name":"Sucursal Norte"}'

# Agregar un producto
curl -X POST http://localhost:8080/api/v1/franchises/{id}/branches/Sucursal Norte/products -H "Content-Type: application/json" -d '{"name":"Laptop HP","stock":50}'

# Consultar producto con mayor stock
curl -X GET http://localhost:8080/api/v1/franchises/{id}/max-stock
```

---

##  Arquitectura — Clean Architecture

```
domain/
 ├── model/        → Entidades (Franchise, Branch, Product)
 ├── api/          → Interfaces de puertos de entrada
 ├── spi/          → Interfaces de puertos de salida
 └── usecase/      → Implementaciones de casos de uso

infrastructure/
 ├── adapter/      → Implementaciones de persistencia (MongoDB)
 ├── entrypoint/   → Handlers + Routers (Spring WebFlux)
 ├── config/       → Swagger, Beans, Validadores
 └── util/         → Constantes
```

> Cada capa es independiente, y las dependencias fluyen hacia el dominio, garantizando un bajo acoplamiento.

---

##  Buenas Prácticas Aplicadas

- Principios **SOLID** y **DRY**
- Enfoque **reactivo y no bloqueante**
- Uso de **Lombok** para reducir boilerplate
- Validaciones con **Jakarta Validation**
- Mapeo de entidades con **MapStruct**
- Manejo de errores mediante **Mono.error()**
- DTOs desacoplados del dominio

---

##  Tecnologías Utilizadas

| Categoría | Tecnologías |
|------------|--------------|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.3 + WebFlux |
| Persistencia | MongoDB Reactive |
| Contenedores | Docker + Docker Compose |
| Testing | JUnit 5, Mockito, Reactor Test |
| Documentación | Swagger OpenAPI 3 |
| Arquitectura | Clean Architecture |

---

##  Documentación Swagger

La documentación de los endpoints está disponible en:  
 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Autor

**John Mike Gutierrez Carvajal**  
_Backend Developer_


---

> Proyecto desarrollado como parte del proceso técnico para **Accenture — Backend Developer (Spring WebFlux)**.  
> Totalmente reproducible mediante Docker.
