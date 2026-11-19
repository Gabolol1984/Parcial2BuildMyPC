# BuildMyPC - Arquitectura de Microservicios Backend

Backend de la plataforma **BuildMyPC**, diseñado bajo una arquitectura distribuida de microservicios con **Spring Boot**, **Spring Cloud**, **Netflix Eureka**, **Spring Cloud Gateway**, **OpenFeign** y **Spring Security con JWT (OAuth2 Resource Server)**.

---

## 1. Arquitectura del Sistema

El backend sigue un patrón de microservicios descentralizados con un punto de entrada único:

```
                          [ Cliente / Frontend React ]
                                       │
                                       ▼ (Puerto 8080)
                         ┌───────────────────────────┐
                         │       msvc-gateway        │ ── Valida JWT (1ra barrera)
                         └───────────────────────────┘    Enruta tráfico vía lb://
                                       │
           ┌───────────────────────────┼───────────────────────────┐
           ▼                           ▼                           ▼
┌─────────────────────┐     ┌─────────────────────┐     ┌─────────────────────┐
│     msvc-eureka     │     │    msvc-usuario     │     │     msvc-build      │
│  (Discovery: 8761)  │     │   (Auth/User: 8082) │     │   (Builds: 8084)    │
└─────────────────────┘     └─────────────────────┘     └─────────────────────┘
           │                           │                           │ (Llama vía Feign)
           │ (Descubrimiento)          │                           ├─► msvc-cpu (8085)
           ▼                           ▼                           ├─► msvc-gpu (8086)
┌─────────────────────────────────────────────────────┐            ├─► msvc-motherboard (8087)
│                  msvc-quotation                     │            ├─► msvc-ram (8088)
│               (Cotizaciones: 8092)                  │            ├─► msvc-powerSupply (8089)
│            Llama a builds y componentes             │            └─► msvc-usuario (8082)
└─────────────────────────────────────────────────────┘
```

### Componentes clave:
1. **msvc-eureka (8761):** Servidor de descubrimiento. Todos los microservicios se registran en él al arrancar.
2. **msvc-gateway (8080):** API Gateway reactivo (WebMVC). Centraliza la seguridad, valida firmas de tokens JWT y balancea la carga hacia los servicios destino (`lb://`).
3. **msvc-usuario (8082):** Emite tokens JWT (`/api/v1/auth/login`, `/api/v1/auth/register`) y gestiona roles.
4. **Catálogo de Componentes (8083, 8085 - 8089):** Microservicios independientes para componentes (`msvc-cpu`, `msvc-gpu`, `msvc-ram`, `msvc-motherboard`, `msvc-powerSupply`, `msvc-component`).
5. **msvc-build (8084):** Orquesta el armado de configuraciones de PC, validando la existencia de cada componente contra los microservicios correspondientes vía OpenFeign.
6. **msvc-quotation (8092):** Genera cotizaciones de builds validadas, calcula subtotales, descuentos y programa el vencimiento de cotizaciones expiradas.

---

## 2. Mapa de Microservicios y Puertos

| Microservicio | Puerto | Nombre Eureka | Ruta Gateway Predeterminada | Descripción |
| :--- | :---: | :--- | :--- | :--- |
| **msvc-eureka** | `8761` | `msvc-eureka` | *(Acceso directo)* | Dashboard de Eureka y registro de instancias |
| **msvc-gateway** | `8080` | `msvc-gateway` | `/` | Punto de entrada único para clientes y Swagger |
| **msvc-usuario** | `8082` | `msvc-usuario` | `/api/v1/auth/**`, `/api/v1/usuarios/**` | Autenticación, registro y gestión de usuarios |
| **msvc-component** | `8083` | `msvc-component` | `/api/v1/componentes/**` | Catálogo maestro general de componentes |
| **msvc-build** | `8084` | `msvc-build` | `/api/v1/builds/**`, `/api/v2/builds/**` | Gestión y armado de configuraciones de PC |
| **msvc-cpu** | `8085` | `msvc-cpu` | `/api/v1/cpus/**`, `/api/v2/cpus/**` | Catálogo de procesadores (CPU) |
| **msvc-gpu** | `8086` | `msvc-gpu` | `/api/v1/gpu/**`, `/api/v2/gpu/**` | Catálogo de tarjetas gráficas (GPU) |
| **msvc-motherboard**| `8087` | `msvc-motherboard` | `/api/v1/motherboards/**`, `/api/v2/motherboards/**` | Catálogo de placas madre |
| **msvc-ram** | `8088` | `msvc-ram` | `/api/v1/ram/**`, `/api/v2/ram/**` | Catálogo de memorias RAM |
| **msvc-powerSupply**| `8089` | `msvc-powerSupply` | `/api/v1/psus/**`, `/api/v2/psus/**` | Catálogo de fuentes de poder (PSU) |
| **msvc-quotation** | `8092` | `msvc-quotation` | `/api/v1/cotizaciones/**` | Generación y aprobación de cotizaciones |

---

## 3. Seguridad y Autenticación (JWT)

- **Algoritmo:** HMAC-SHA256 (`HS256`) con clave secreta compartida (`JWT_SECRET`).
- **Flujo:**
  1. El cliente envía credenciales a `POST http://localhost:8080/api/v1/auth/login`.
  2. `msvc-usuario` valida y devuelve un token JWT con el claim `roles`.
  3. El cliente adjunta el token en cada petición en el encabezado:  
     `Authorization: Bearer <TOKEN>`
  4. El **Gateway** valida la firma y vigencia del token.
  5. Cada microservicio destino actúa como **OAuth2 Resource Server**, valida el token y autoriza según los roles (`ADMIN`, `TECNICO`, `USUARIO`).
  6. Para la comunicación interna (ej: `build` llamando a `ram`), `FeignClientConfig` reenvía automáticamente la cabecera `Authorization` para que las llamadas entre servicios no fallen con 401.

### Usuarios y Roles precargados (Semilla)
Al arrancar `msvc-usuario`, se crean automáticamente los siguientes usuarios de prueba:

| Usuario | Contraseña | Roles | Permisos Principales |
| :--- | :--- | :--- | :--- |
| `admin` | `admin123` | `ROLE_ADMIN` | Acceso total, crear/editar/eliminar componentes y listar usuarios |
| `tecnico1` | `tecnico123` | `ROLE_TECNICO` | Crear y modificar componentes y cotizaciones |
| `usuario1` | `usuario123` | `ROLE_USUARIO` | Lectura de componentes, creación de builds y cotizaciones |

---

## 4. Requisitos Previos

- **Java JDK:** Versión 17, 21 o superior.
- **Maven:** 3.8+ o utilizar el wrapper `./mvnw` incluido.
- **Git**

---

## 5. Configuración del Entorno (`.env`)

El backend incluye archivos `.env` configurados con valores por defecto para desarrollo local. Si deseas modificarlos, puedes ajustar las siguientes variables:

```properties
# Clave simétrica de al menos 32 caracteres (256 bits) para HMAC-SHA256
JWT_SECRET=supersecretjwtkeyforbuildmypcproject1234567890!

# Credenciales de base de datos H2 en memoria
DB_USERNAME=sa
DB_PASSWORD=
```

---

## 6. Guía de Ejecución Paso a Paso

Para que los microservicios se descubran y enruten correctamente, deben iniciarse en el siguiente **orden recomendado**:

### Paso 1: Iniciar el Servidor Eureka (OBLIGATORIO PRIMERO)
Abre una terminal en la carpeta raíz y ejecuta:
```bash
cd msvc-eureka
..\mvnw spring-boot:run
```
> Comprueba que la consola indique que levantó en el puerto `8761`.  
> Abre en el navegador: [http://localhost:8761](http://localhost:8761) para ver el panel de Eureka.

---

### Paso 2: Iniciar el API Gateway
En una segunda terminal:
```bash
cd msvc-gateway
..\mvnw spring-boot:run
```
> El Gateway quedará escuchando en el puerto `8080`.

---

### Paso 3: Iniciar el Servicio de Usuarios y Autenticación
En una tercera terminal:
```bash
cd msvc-usuario
..\mvnw spring-boot:run
```
> Al arrancar en el puerto `8082`, cargará los usuarios semilla (`admin`, `tecnico1`, `usuario1`).

---

### Paso 4: Iniciar los Microservicios de Componentes
En terminales separadas (o desde tu IDE preferido):
```bash
# CPU (8085)
cd msvc-cpu && ..\mvnw spring-boot:run

# GPU (8086)
cd msvc-gpu && ..\mvnw spring-boot:run

# Motherboard (8087)
cd msvc-motherboard && ..\mvnw spring-boot:run

# RAM (8088)
cd msvc-ram && ..\mvnw spring-boot:run

# Fuente de Poder (8089)
cd msvc-powerSupply && ..\mvnw spring-boot:run

# Componentes Generales (8083)
cd msvc-component && ..\mvnw spring-boot:run
```

---

### Paso 5: Iniciar los Servicios de Negocio (Builds y Cotizaciones)
```bash
# Builds (8084)
cd msvc-build && ..\mvnw spring-boot:run

# Cotizaciones (8092)
cd msvc-quotation && ..\mvnw spring-boot:run
```

> **Verificación:** Refresca el panel de Eureka en [http://localhost:8761](http://localhost:8761). Deberás ver todos los microservicios listados bajo la columna **Instances currently registered with Eureka**.

---

## 7. Pruebas y Uso de la API

Todo el tráfico debe pasar por el **API Gateway en el puerto `8080`**.

### 1. Iniciar Sesión para Obtener el Token JWT
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

### 2. Consultar Componentes usando el Token
```bash
curl -X GET http://localhost:8080/api/v1/cpus \
  -H "Authorization: Bearer <PEGAR_TOKEN_AQUI>"
```

### 3. Crear una Build de PC
```bash
curl -X POST http://localhost:8080/api/v1/builds \
  -H "Authorization: Bearer <PEGAR_TOKEN_AQUI>" \
  -H "Content-Type: application/json" \
  -d '{
    "usuarioId": 1,
    "cpuId": 1,
    "gpuId": 1,
    "motherboardId": 1,
    "ramId": 1,
    "fuenteId": 1,
    "estado": "BORRADOR"
  }'
```

---

## 8. Documentación Swagger y Consolas de Base de Datos

- **Swagger UI Centralizado (Todos los microservicios en una página con selector desplegable):**  
  👉 [http://localhost:8080/docs/swagger-ui.html](http://localhost:8080/docs/swagger-ui.html)

- **Panel de Descubrimiento Eureka:**  
  👉 [http://localhost:8761](http://localhost:8761)

- **Consolas H2 en Memoria:**  
  Cada microservicio expone su consola H2 en `/h2-console` en su respectivo puerto (ej. `http://localhost:8082/h2-console`, `http://localhost:8084/h2-console`).
  - **JDBC URL:** indicada en su respectivo `application.properties` (ej: `jdbc:h2:mem:db_usuarios`)
  - **User:** `sa`
  - **Password:** *(en blanco)*

---

## 9. Ejecución de Pruebas Unitarias

Para correr las pruebas de cualquier módulo de forma aislada:
```bash
cd msvc-cpu
..\mvnw test
```
O para ejecutar todas las pruebas del proyecto desde la raíz:
```bash
.\mvnw test
```
