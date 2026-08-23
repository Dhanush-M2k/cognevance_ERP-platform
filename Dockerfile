# ---------- Stage 1: Build ----------
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Cache dependencies in their own layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build the application
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- Stage 2: Run (Render production image) ----------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Render's runtime is Debian-based; create a non-root user (Render's
# recommended best practice for containers) instead of running as root
RUN useradd --system --create-home --shell /bin/false appuser
USER appuser

COPY --chown=appuser:appuser --from=build /app/target/erp-platform.jar app.jar

# Render always sets $PORT at runtime and expects the container to bind
# to it on 0.0.0.0 - no local default needed, and no EXPOSE is required
# since Render ignores the Dockerfile EXPOSE directive and routes based
# on the port your app actually listens on.
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.address=0.0.0.0 --server.port=${PORT}"]
