# =====================================================================
# Frontend React : image en deux étapes (SmartDeploy, PFE)
# 1. Construction des fichiers statiques avec Node.
# 2. Service par Nginx non privilégié (port 8080), qui relaie /api/
#    vers le backend : même origine, donc ni URL à injecter ni CORS.
# =====================================================================
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
# URL relatives : le navigateur appelle le frontend, Nginx relaie au backend.
ARG VITE_GET_MESSAGES_URL=/api/allmessages
ARG VITE_POST_MESSAGES_URL=/api/sendmessage
ARG VITE_DELETE_MESSAGES_URL=/api/deletemessage
RUN npm run build

FROM nginxinc/nginx-unprivileged:1.27-alpine

# Adresse du backend dans le cluster (Service Kubernetes généré par SmartDeploy).
ENV BACKEND_URL=http://fullstack-demo-backend-service:8080

COPY nginx.conf.template /etc/nginx/templates/default.conf.template
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 8080
