FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

# --- add curl for health-checks ---
RUN apk add --no-cache curl

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
