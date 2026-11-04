# =====================================================================
# Backend Spring Boot : image en deux étapes (SmartDeploy, PFE)
# 1. Construction avec le JDK ; les dépendances Maven sont téléchargées
#    dans une couche séparée, réutilisée tant que pom.xml ne change pas.
# 2. Exécution avec le JRE seul, sans code source ni cache Maven,
#    par un utilisateur non privilégié.
# =====================================================================
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline

COPY src src
RUN ./mvnw -B -q package -DskipTests \
 && cp target/*.jar app.jar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S app && adduser -S app -G app
COPY --from=build --chown=app:app /workspace/app.jar app.jar
USER app

EXPOSE 8080
# La JVM adapte sa mémoire à la limite du conteneur.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]
