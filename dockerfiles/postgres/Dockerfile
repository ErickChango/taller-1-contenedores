# ==========================================
# Dockerfile para PostgreSQL
# ==========================================
# Este Dockerfile crea una imagen personalizada de PostgreSQL
# basada en la imagen oficial con configuraciones adicionales

# ==========================================
# Imagen base de PostgreSQL
# ==========================================
FROM postgres:16-alpine

# Metadatos de la imagen
LABEL maintainer="tu-email@example.com"
LABEL description="PostgreSQL 16 personalizado para aplicaciones"
LABEL version="1.0"

# ==========================================
# Variables de entorno por defecto
# ==========================================
# Estas pueden ser sobrescritas al ejecutar el contenedor
ENV POSTGRES_DB=mediadb
ENV POSTGRES_USER=mediauser
ENV POSTGRES_PASSWORD=mediapass123
ENV PGDATA=/var/lib/postgresql/data/pgdata

# ==========================================
# Configuraciones personalizadas de PostgreSQL
# ==========================================
# Copiar archivo de configuración personalizada (opcional)
COPY postgresql.conf /etc/postgresql/postgresql.conf

# Copiar scripts de inicialización (opcional)
# Los archivos .sql, .sql.gz, .sh se ejecutan automáticamente al crear la BD
COPY init-scripts/ /docker-entrypoint-initdb.d/

# ==========================================
# Instalar extensiones adicionales (opcional)
# ==========================================
# Descomentar si necesitas instalar extensiones
# RUN apk add --no-cache postgresql-contrib

# ==========================================
# Crear directorios necesarios
# ==========================================
RUN mkdir -p /var/lib/postgresql/data && \
    chown -R postgres:postgres /var/lib/postgresql

# Exponer el puerto estándar de PostgreSQL
EXPOSE 5432

# Volumen para persistencia de datos
VOLUME ["/var/lib/postgresql/data"]

# Healthcheck para verificar que PostgreSQL está listo
HEALTHCHECK --interval=10s --timeout=5s --retries=5 \
    CMD pg_isready -U $POSTGRES_USER -d $POSTGRES_DB || exit 1

# El comando por defecto ya está definido en la imagen base
# CMD ["postgres"]

# ==========================================
# Instrucciones de uso:
# ==========================================
# 1. Construir la imagen:
#    docker build -t postgres-custom:16 .
#
# 2. Ejecutar el contenedor con volumen persistente:
#    docker run -d \
#      --name postgres-container \
#      -p 5432:5432 \
#      -e POSTGRES_DB=mydb \
#      -e POSTGRES_USER=myuser \
#      -e POSTGRES_PASSWORD=mypassword \
#      -v postgres-data:/var/lib/postgresql/data \
#      postgres-custom:16
#
# 3. Conectarse a PostgreSQL:
#    docker exec -it postgres-container psql -U myuser -d mydb
#
# 4. Realizar backup:
#    docker exec postgres-container pg_dump -U myuser mydb > backup.sql
#
# 5. Restaurar backup:
#    docker exec -i postgres-container psql -U myuser mydb < backup.sql
# ==========================================
