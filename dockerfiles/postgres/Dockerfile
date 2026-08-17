FROM postgres:16-alpine

LABEL maintainer="tu-email@example.com"
LABEL description="PostgreSQL 16 personalizado para aplicaciones"
LABEL version="1.0"

ENV POSTGRES_DB=mediadb
ENV POSTGRES_USER=mediauser
ENV POSTGRES_PASSWORD=mediapass123
ENV PGDATA=/var/lib/postgresql/data/pgdata

COPY postgresql.conf /etc/postgresql/postgresql.conf

COPY init-scripts/ /docker-entrypoint-initdb.d/

RUN mkdir -p /var/lib/postgresql/data && \
    chown -R postgres:postgres /var/lib/postgresql

EXPOSE 5432

VOLUME ["/var/lib/postgresql/data"]

HEALTHCHECK --interval=10s --timeout=5s --retries=5 \
    CMD pg_isready -U $POSTGRES_USER -d $POSTGRES_DB || exit 1
