# ==========================================
# Dockerfile para Angular
# ==========================================
# Este Dockerfile crea una imagen optimizada para aplicaciones Angular
# Utiliza build multi-etapa para reducir el tamaño final de la imagen

# ==========================================
# ETAPA 1: Construcción (Build Stage)
# ==========================================
FROM node:18-alpine AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de dependencias
COPY package*.json ./

# Instalar dependencias del proyecto
RUN npm ci --only=production

# Copiar el código fuente de la aplicación
COPY . .

# Construir la aplicación Angular para producción
# --configuration production optimiza el bundle
RUN npm run build -- --configuration production

# ==========================================
# ETAPA 2: Producción (Production Stage)
# ==========================================
FROM nginx:alpine

# Copiar configuración personalizada de nginx (opcional)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copiar los archivos construidos desde la etapa de build
# Angular genera los archivos en dist/<nombre-proyecto>
COPY --from=build /app/dist/* /usr/share/nginx/html/

# Exponer el puerto 80 para HTTP
EXPOSE 80

# Comando para iniciar nginx en primer plano
CMD ["nginx", "-g", "daemon off;"]

# ==========================================
# Instrucciones de uso:
# ==========================================
# 1. Construir la imagen:
#    docker build -t angular-app:1.0 .
#
# 2. Ejecutar el contenedor:
#    docker run -d -p 4200:80 --name angular-container angular-app:1.0
#
# 3. Acceder a la aplicación:
#    http://localhost:4200
# ==========================================
