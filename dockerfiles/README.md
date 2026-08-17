# 🐳 Dockerfiles - Taller de Contenedores

Este directorio contiene los Dockerfiles de las 3 tecnologías principales del stack:

## 📁 Estructura del Proyecto

```
dockerfiles/
├── angular/
│   ├── Dockerfile
│   ├── nginx.conf
│   └── .dockerignore
├── spring-boot/
│   ├── Dockerfile
│   └── .dockerignore
├── postgres/
│   ├── Dockerfile
│   ├── postgresql.conf
│   ├── init-scripts/
│   │   └── 01-create-schema.sql
│   └── .dockerignore
└── README.md
```

---

## 🎯 1. Angular Dockerfile

**Ubicación:** `dockerfiles/angular/Dockerfile`

### Características:
- ✅ Build multi-etapa (Node.js + Nginx)
- ✅ Optimización de producción con `--configuration production`
- ✅ Servidor Nginx Alpine ligero
- ✅ Configuración SPA para routing de Angular
- ✅ Compresión Gzip habilitada
- ✅ Caché de archivos estáticos

### Comandos:

```bash
# Construir imagen
cd dockerfiles/angular
docker build -t angular-app:1.0 .

# Ejecutar contenedor
docker run -d -p 4200:80 --name angular-container angular-app:1.0

# Ver logs
docker logs angular-container -f

# Acceder a la aplicación
http://localhost:4200
```

### Tamaño aproximado:
- Imagen final: ~45 MB (Nginx Alpine + Angular build)

---

## 🎯 2. Spring Boot Dockerfile

**Ubicación:** `dockerfiles/spring-boot/Dockerfile`

### Características:
- ✅ Build multi-etapa (Maven + JRE)
- ✅ Caché de dependencias Maven
- ✅ JRE 17 Alpine ligero (sin JDK completo)
- ✅ Usuario no-root para seguridad
- ✅ Variables de entorno configurables
- ✅ Optimización de memoria JVM

### Comandos:

```bash
# Construir imagen
cd dockerfiles/spring-boot
docker build -t spring-boot-app:1.0 .

# Ejecutar contenedor simple
docker run -d -p 8080:8080 --name spring-container spring-boot-app:1.0

# Ejecutar con variables de entorno
docker run -d -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/mediadb \
  -e SPRING_DATASOURCE_USERNAME=mediauser \
  -e SPRING_DATASOURCE_PASSWORD=mediapass123 \
  -e JAVA_OPTS="-Xmx1024m -Xms512m" \
  --name spring-container \
  spring-boot-app:1.0

# Ver logs
docker logs spring-container -f

# Acceder a la aplicación
http://localhost:8080
```

### Tamaño aproximado:
- Imagen final: ~220 MB (JRE 17 Alpine + JAR)

---

## 🎯 3. PostgreSQL Dockerfile

**Ubicación:** `dockerfiles/postgres/Dockerfile`

### Características:
- ✅ Basado en PostgreSQL 16 Alpine
- ✅ Configuración personalizada (postgresql.conf)
- ✅ Scripts de inicialización automática
- ✅ Health check integrado
- ✅ Extensiones UUID y pg_trgm
- ✅ Volumen para persistencia de datos

### Comandos:

```bash
# Construir imagen
cd dockerfiles/postgres
docker build -t postgres-custom:16 .

# Ejecutar contenedor con volumen
docker run -d \
  --name postgres-container \
  -p 5432:5432 \
  -e POSTGRES_DB=mediadb \
  -e POSTGRES_USER=mediauser \
  -e POSTGRES_PASSWORD=mediapass123 \
  -v postgres-data:/var/lib/postgresql/data \
  postgres-custom:16

# Conectarse a PostgreSQL
docker exec -it postgres-container psql -U mediauser -d mediadb

# Ver logs
docker logs postgres-container -f

# Realizar backup
docker exec postgres-container pg_dump -U mediauser mediadb > backup.sql

# Restaurar backup
docker exec -i postgres-container psql -U mediauser mediadb < backup.sql
```

### Tamaño aproximado:
- Imagen final: ~240 MB (PostgreSQL 16 Alpine)

---

## 🚀 Uso Completo con Docker Compose

Para usar las 3 tecnologías juntas, crea un `docker-compose.yml`:

```yaml
services:
  postgres-db:
    build: ./dockerfiles/postgres
    container_name: postgres-db
    environment:
      POSTGRES_DB: mediadb
      POSTGRES_USER: mediauser
      POSTGRES_PASSWORD: mediapass123
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data
    networks:
      - app-network

  spring-backend:
    build: ./dockerfiles/spring-boot
    container_name: spring-backend
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-db:5432/mediadb
      SPRING_DATASOURCE_USERNAME: mediauser
      SPRING_DATASOURCE_PASSWORD: mediapass123
    ports:
      - "8080:8080"
    depends_on:
      - postgres-db
    networks:
      - app-network

  angular-frontend:
    build: ./dockerfiles/angular
    container_name: angular-frontend
    ports:
      - "4200:80"
    depends_on:
      - spring-backend
    networks:
      - app-network

volumes:
  postgres-data:

networks:
  app-network:
    driver: bridge
```

### Comandos Docker Compose:

```bash
# Construir y levantar todos los servicios
docker-compose up --build -d

# Ver estado de servicios
docker-compose ps

# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f spring-backend

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v
```

---

## 📊 Comparativa de Imágenes

| Tecnología | Imagen Base | Tamaño Final | Etapas | Optimización |
|------------|-------------|--------------|--------|--------------|
| **Angular** | node:18-alpine + nginx:alpine | ~45 MB | 2 | Alta |
| **Spring Boot** | maven:3.9 + temurin:17-jre | ~220 MB | 2 | Alta |
| **PostgreSQL** | postgres:16-alpine | ~240 MB | 1 | Media |

---

## 🔧 Personalización

### Angular:
- Modifica `nginx.conf` para ajustar configuración del servidor
- Ajusta rutas de build en `Dockerfile` según tu proyecto

### Spring Boot:
- Modifica `JAVA_OPTS` para ajustar memoria JVM
- Agrega variables de entorno personalizadas
- Ajusta nombre del JAR si es diferente

### PostgreSQL:
- Modifica `postgresql.conf` para ajustar rendimiento
- Agrega scripts SQL en `init-scripts/` para inicialización
- Instala extensiones adicionales según necesidad

---

## 📝 Notas Importantes

1. **Seguridad:** 
   - Cambia las contraseñas por defecto en producción
   - Usa secrets de Docker o variables de entorno seguras
   - El usuario Spring Boot no es root (buena práctica)

2. **Persistencia:**
   - Siempre usa volúmenes para PostgreSQL
   - Los datos sobreviven al ciclo de vida del contenedor

3. **Optimización:**
   - Las imágenes multi-etapa reducen ~60% el tamaño
   - El .dockerignore excluye archivos innecesarios
   - Las dependencias se cachean para builds más rápidos

4. **Desarrollo vs Producción:**
   - Estos Dockerfiles están optimizados para producción
   - Para desarrollo, considera usar bind mounts y hot-reload

---

## 🎓 Conclusión

Estos Dockerfiles demuestran las mejores prácticas para contenerización:
- ✅ Imágenes Alpine ligeras
- ✅ Builds multi-etapa
- ✅ Optimización de caché
- ✅ Configuración mediante variables de entorno
- ✅ Health checks y seguridad
- ✅ Persistencia de datos

**Taller 1 - Infraestructura Base y Backend con Persistencia**
