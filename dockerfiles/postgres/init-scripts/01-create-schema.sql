-- ==========================================
-- Script de inicialización de PostgreSQL
-- ==========================================
-- Este script se ejecuta automáticamente al crear el contenedor por primera vez

-- Crear extensiones útiles
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- Crear esquema personalizado (opcional)
-- CREATE SCHEMA IF NOT EXISTS app_schema;

-- Crear tabla de ejemplo (opcional)
-- CREATE TABLE IF NOT EXISTS users (
--     id SERIAL PRIMARY KEY,
--     username VARCHAR(50) UNIQUE NOT NULL,
--     email VARCHAR(100) UNIQUE NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

-- Mensaje de confirmación
\echo 'Base de datos inicializada correctamente'
