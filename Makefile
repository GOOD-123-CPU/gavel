# Gavel — developer shortcuts
# Requires: JDK 17, Maven 3.9+, Docker (optional)

JAVA_HOME ?= D:/env/jdk-17.0.12
MAVEN_HOME ?= D:/env/apache-maven-3.9.5
MVN ?= $(MAVEN_HOME)/bin/mvn.cmd

.PHONY: help build run clean docker-build docker-up docker-down db-init

help:
	@echo "Gavel — Open-source Online Auction Platform"
	@echo "  make build        Compile and package the server jar"
	@echo "  make run          Run the server locally (needs MySQL with gavel db)"
	@echo "  make db-init      Import db.sql into local MySQL (root/123456)"
	@echo "  make docker-up    Start MySQL + app via docker compose"
	@echo "  make docker-down  Stop the compose stack"
	@echo "  make clean        Remove build artifacts"

build:
	cd server && $(MVN) clean package -DskipTests

run: db-init
	cd server && java -jar target/gavel-server-2.0.0.jar

db-init:
	mysql -uroot -p123456 < db.sql

docker-up:
	docker compose up -d --build

docker-down:
	docker compose down

clean:
	cd server && $(MVN) clean
