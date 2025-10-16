.PHONY: help \
dev-build dev-run dev-exec dev-test dev-verify dev-stop dev-restart javadoc \
docker-package docker-build docker-run stop-app logs \
ensure-ci-net db-up db-wait db-down


IMAGE_DEV      ?= dev-mvn:latest
DEV_CONTAINER  ?= dev-mvn-run
MVN_OPTS       ?= -Dproject.build.sourceEncoding=UTF-8 -Dfile.encoding=UTF-8
POM_PATH       ?= /app/pom.xml
MOUNT_DIR      ?= $(PWD)/backend

IMAGE_APP      ?= monapp:local
APP_CONTAINER  ?= monapp-run

CI_NET         ?= ci_net
DB_CONTAINER   ?= mariadb-ci
DB_NAME        ?= projetml
DB_USER        ?= mluser
DB_PASS        ?= mlpass
DB_ROOT_PASS   ?= superroot
DB_PORT        ?= 3306

help:
	@echo "=== DEV ==="
	@echo "make dev-build     -> build image dev (Ubuntu + JDK17 + Maven + Git)"
	@echo "make dev-run       -> démarre le conteneur de dev monté sur ./backend"
	@echo "make dev-exec      -> shell dans le conteneur de dev"
	@echo "make dev-test      -> mvn clean test"
	@echo "make dev-verify    -> mvn verify (avec JaCoCo)"
	@echo "make dev-stop      -> supprime le conteneur de dev"
	@echo "make dev-restart   -> restart conteneur de dev"
	@echo "make javadoc       -> génère la JavaDoc (target/site/apidocs)"
	@echo ""
	@echo "=== RUNTIME ==="
	@echo "make docker-package -> produit le JAR (dans le conteneur de dev)"
	@echo "make docker-build   -> build image runtime monapp:local (Dockerfile.app)"
	@echo "make docker-run     -> lance DB + app (port 8888)"
	@echo "make logs           -> logs de l'app"
	@echo "make stop-app       -> supprime le conteneur d'app"
	@echo "make db-up          -> lance MariaDB"
	@echo "make db-down        -> stop DB"
	@echo ""

dev-build:
	@docker build -t $(IMAGE_DEV) -f Dockerfile.dev .

dev-run:
	docker run -d --name $(DEV_CONTAINER) \
		-v $(MOUNT_DIR):/app -w /app \
		$(IMAGE_DEV) tail -f /dev/null

dev-exec:
	docker exec -it $(DEV_CONTAINER) bash

dev-test:
	docker exec -it $(DEV_CONTAINER) mvn -B -f $(POM_PATH) $(MVN_OPTS) clean test

dev-verify:
	docker exec -it $(DEV_CONTAINER) mvn -B -f $(POM_PATH) $(MVN_OPTS) verify

dev-stop:
	-@docker rm -f $(DEV_CONTAINER) 2>/dev/null || true

dev-restart: dev-stop dev-run

javadoc:
	docker exec -it $(DEV_CONTAINER) mvn -B -f $(POM_PATH) javadoc:javadoc


docker-package:
	docker exec -it $(DEV_CONTAINER) mvn -B -f $(POM_PATH) clean package

docker-build:
	docker build -t $(IMAGE_APP) -f Dockerfile.app .

ensure-ci-net:
	-@docker network create $(CI_NET) 2>/dev/null || true

db-up: ensure-ci-net
	-@docker rm -f $(DB_CONTAINER) 2>/dev/null || true
	docker run -d --name $(DB_CONTAINER) --network $(CI_NET) \
		-e MARIADB_ROOT_PASSWORD=$(DB_ROOT_PASS) \
		-e MARIADB_DATABASE=$(DB_NAME) \
		-e MARIADB_USER=$(DB_USER) \
		-e MARIADB_PASSWORD=$(DB_PASS) \
		-p $(DB_PORT):3306 \
		mariadb:11.4

db-wait:
	@echo "Attente de la DB $(DB_CONTAINER) ..."
	@for i in $$(seq 1 30); do \
	  docker exec $(DB_CONTAINER) mariadb -u$(DB_USER) -p$(DB_PASS) -e "SELECT 1" >/dev/null 2>&1 && { echo "DB OK"; exit 0; }; \
	  sleep 2; \
	done; \
	echo "DB pas prête après attente." && exit 1

docker-run: stop-app ensure-ci-net db-up db-wait
	docker run -d --name $(APP_CONTAINER) --network $(CI_NET) -p 8888:8888 \
		-e SPRING_DATASOURCE_URL="jdbc:mariadb://$(DB_CONTAINER):$(DB_PORT)/$(DB_NAME)" \
		-e SPRING_DATASOURCE_USERNAME="$(DB_USER)" \
		-e SPRING_DATASOURCE_PASSWORD="$(DB_PASS)" \
		-e SERVER_PORT=8888 \
		$(IMAGE_APP)

stop-app:
	-@docker rm -f $(APP_CONTAINER) 2>/dev/null || true

logs:
	docker logs -f $(APP_CONTAINER)

db-down:
	-@docker rm -f $(DB_CONTAINER) 2>/dev/null || true
