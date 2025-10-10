# ---------- build ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---------- runtime (Debian 13 / trixie) ----------
FROM debian:13-slim

# Version de geckodriver (cámbiala si quieres otra)
ARG GECKODRIVER_VERSION=0.35.0

# Java 21 + Firefox ESR + utilidades para descargar, y luego geckodriver desde GitHub
RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends \
      openjdk-21-jre-headless \
      firefox-esr \
      ca-certificates \
      curl \
      tar \
      fonts-liberation; \
    \
    arch="$(dpkg --print-architecture)"; \
    case "$arch" in \
      amd64)  gd_arch="linux64" ;; \
      arm64)  gd_arch="linux-aarch64" ;; \
      *) echo "Arquitectura no soportada: $arch" && exit 1 ;; \
    esac; \
    curl -fsSL -o /tmp/geckodriver.tar.gz \
      "https://github.com/mozilla/geckodriver/releases/download/v${GECKODRIVER_VERSION}/geckodriver-v${GECKODRIVER_VERSION}-${gd_arch}.tar.gz"; \
    tar -xzf /tmp/geckodriver.tar.gz -C /usr/local/bin geckodriver; \
    rm -f /tmp/geckodriver.tar.gz; \
    chmod +x /usr/local/bin/geckodriver; \
    \
    # limpieza
    rm -rf /var/lib/apt/lists/*

# (opcional) verificar versiones durante el build
RUN java -version && firefox --version && geckodriver --version

WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar

ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]