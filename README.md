# Gavel

Gavel is a full-stack online auction system built with Spring Boot, MySQL, Vue 2, and a classic HTML storefront. It includes a member-facing auction portal, an administrator console, seed data, API documentation, Docker support, and a small test suite around security-sensitive behavior.

This repository is intended for learning, coursework, demonstrations, and secondary development. It is not a drop-in production auction platform.

[Chinese README](README.zh-CN.md) · [License](LICENSE) · [Security Policy](SECURITY.md) · [Contributing](CONTRIBUTING.md)

![Gavel banner](docs/assets/banner.svg)

## What It Includes

- Member storefront for browsing auction items, reading announcements, registering, signing in, bidding, viewing bid history, and leaving messages.
- Admin console for managing users, members, categories, auction items, bid records, orders, messages, announcements, and site configuration.
- Spring Boot backend with REST-style controllers, MyBatis-Plus mappers, token-based authentication, Swagger UI, and Actuator health checks.
- MySQL schema and fictional seed data in [db.sql](db.sql).
- Docker Compose setup for running MySQL and the application together.
- GitHub Actions CI, issue templates, pull request template, Dependabot configuration, MIT license, and security reporting notes.

## Screenshots

| Storefront home | Auction items |
| --- | --- |
| ![Storefront home](docs/screenshots/storefront-home.png) | ![Auction items](docs/screenshots/storefront-items.png) |

| Admin console | API documentation |
| --- | --- |
| ![Admin dashboard](docs/screenshots/admin-dashboard.png) | ![Swagger UI](docs/screenshots/swagger-ui.png) |

## Tech Stack

| Area | Technology |
| --- | --- |
| Backend | Java 17, Spring Boot 3.5, MyBatis-Plus 3.5 |
| Database | MySQL 8 |
| API documentation | springdoc-openapi, Swagger UI |
| Admin frontend | Vue 2, Element UI |
| Storefront | HTML, jQuery, Layui |
| Build and delivery | Maven, Docker, Docker Compose, GitHub Actions |
| Security-related utilities | BCrypt password hashing, token interceptor, upload extension whitelist, SQL identifier validation |

## Quick Start With Docker

Docker is the simplest way to run the project because it starts MySQL, imports the schema, and runs the application with one command.

Requirements:

- Docker Desktop or Docker Engine with Compose support
- Port `8080` available for the application
- Port `3306` available for MySQL, unless you change the Compose mapping

Run from the repository root:

```bash
docker compose up -d
docker compose logs -f app
```

When the app is ready, open:

| Page | URL | Account |
| --- | --- | --- |
| Storefront | http://localhost:8080/front/index.html | `demo` / `demo123` |
| Admin console | http://localhost:8080/admin/dist/index.html | `abo` / `abo` |
| Swagger UI | http://localhost:8080/swagger-ui.html | No login required |
| Health check | http://localhost:8080/actuator/health | No login required |

Stop the services:

```bash
docker compose down
```

Remove the database volume and start from a fresh import:

```bash
docker compose down -v
docker compose up -d
```

The seed database contains image paths under `/upload/...`. This release intentionally does not include runtime upload files, so some demo images may show as missing until you upload replacement images through the admin console or place your own files in the runtime `upload/` directory.

## Run Locally

Requirements:

- JDK 17
- Maven 3.9 or the included Maven Wrapper
- MySQL 8

Create the database and import seed data:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS gavel DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p gavel < db.sql
```

Configure the datasource in [server/src/main/resources/application.yml](server/src/main/resources/application.yml), or override it with environment variables:

```bash
SPRING_DATASOURCE_URL=jdbc:mysql://127.0.0.1:3306/gavel?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=123456
```

Build and run:

```bash
cd server
./mvnw clean package -DskipTests
java -jar target/gavel-server-2.0.0.jar
```

On Windows PowerShell:

```powershell
cd server
.\mvnw.cmd clean package -DskipTests
java -jar target\gavel-server-2.0.0.jar
```

## Configuration

The main application configuration is in [server/src/main/resources/application.yml](server/src/main/resources/application.yml).

Common settings:

| Setting | Purpose | Default |
| --- | --- | --- |
| `server.port` | HTTP port | `8080` |
| `spring.datasource.url` | MySQL JDBC URL | local `gavel` database |
| `spring.datasource.username` | MySQL username | `root` |
| `spring.datasource.password` | MySQL password | `123456` |
| `app.cors.allowed-origins` | Allowed browser origins | `http://localhost:8080` |
| `spring.servlet.multipart.max-file-size` | Single upload limit | `10MB` |
| `spring.servlet.multipart.max-request-size` | Request upload limit | `10MB` |

For Docker Compose, copy [.env.example](.env.example) to `.env` and adjust values:

```bash
cp .env.example .env
```

Do not commit `.env`.

## Project Structure

```text
.
|-- db.sql
|-- docker-compose.yml
|-- Makefile
|-- README.md
|-- README.zh-CN.md
|-- docs/
|   |-- assets/
|   `-- screenshots/
`-- server/
    |-- pom.xml
    |-- Dockerfile
    |-- .mvn/wrapper/
    `-- src/
        |-- main/
        |   |-- java/com/gavel/
        |   |   |-- config/
        |   |   |-- controller/
        |   |   |-- dao/
        |   |   |-- entity/
        |   |   |-- interceptor/
        |   |   |-- service/
        |   |   `-- utils/
        |   `-- resources/
        |       |-- mapper/
        |       |-- admin/
        |       |-- front/
        |       `-- application.yml
        `-- test/java/com/gavel/
```

Notes:

- The admin frontend source and built `dist` files are both included under `server/src/main/resources/admin/admin`.
- The storefront is included under `server/src/main/resources/front/front`.
- Uploaded runtime files are stored under `upload/` and are intentionally ignored by Git.

## Main Modules

| Module | Description |
| --- | --- |
| `users` | Administrator login, session, password reset, and user management |
| `members` | Member registration, login, profile, and management |
| `auction-items` | Auction item listing and management |
| `categories` | Auction category management |
| `bid-history` | Bid records and review state |
| `orders` | Orders generated from successful bidding flows |
| `news` | Announcements and public content |
| `messages` | Member message board |
| `file` | Upload and download endpoints |
| `config` | Banner and runtime configuration records |

Most business controllers follow the same endpoint pattern:

```text
/page
/list
/lists
/query
/info/{id}
/detail/{id}
/save
/add
/update
/delete
/remind/{columnName}/{type}
```

Use Swagger UI at http://localhost:8080/swagger-ui.html for the exact request and response shapes.

File endpoints are exposed under `/file`, for example `/file/upload` and `/file/download/{fileName}`.

## Authentication

The backend uses an opaque token stored in the database. Login endpoints return a token, and protected endpoints expect it in the `Token` request header.

Example:

```bash
curl -X POST http://localhost:8080/users/login \
  -d "username=abo" \
  -d "password=abo"
```

Then call protected endpoints with:

```bash
curl http://localhost:8080/users/session \
  -H "Token: <token-from-login>"
```

## Seed Accounts

The seed data in [db.sql](db.sql) contains fictional accounts:

| Role | Username | Password |
| --- | --- | --- |
| Admin | `abo` | `abo` |
| Member | `demo` | `demo123` |
| Member | `test` | `test123` |

Change these passwords before exposing any deployment outside your local machine.

## Tests

Run backend tests:

```bash
cd server
./mvnw test
```

On Windows PowerShell:

```powershell
cd server
.\mvnw.cmd test
```

Current test coverage focuses on behavior that is easy to regress during maintenance:

| Test | Coverage |
| --- | --- |
| `SQLFilterTest` | SQL identifier validation and whitelist checks |
| `CommonControllerWhitelistTest` | Dynamic option/follow endpoints reject unapproved table or column access |
| `FileUploadWhitelistTest` | Upload endpoint rejects dangerous file extensions |
| `PasswordVerificationTest` | BCrypt verification and legacy plaintext password upgrade |
| `RTest` | Response wrapper contract |

## Admin Frontend Development

The admin frontend is a Vue 2 application. The built output is already committed so the Spring Boot app can serve the admin console directly.

To work on the admin frontend:

```bash
cd server/src/main/resources/admin/admin
npm install
npm run serve
```

Build the admin frontend:

```bash
npm run build
```

## Security Notes

This project has several hardening measures compared with many generated or coursework-style Spring Boot projects:

- Passwords in the seed data are BCrypt hashes.
- Legacy plaintext password verification is upgraded on successful login.
- Dynamic table and column access is restricted by whitelist checks.
- Uploads use generated filenames and reject dangerous extensions.
- CORS is configured through an allowlist.
- Generated build output, runtime upload files, logs, personal documents, and environment files are ignored.

Important limitations:

- The default credentials are public and are only for local demonstration.
- The token model is simple and database-backed; it is suitable for learning, not high-risk production use.
- There is no rate limiting, payment gateway integration, audit log system, or real-time bidding engine.
- You should perform your own security review before deploying this project publicly.

Report vulnerabilities through the process in [SECURITY.md](SECURITY.md). Do not disclose security issues in public GitHub issues.

## Common Problems

### Maven wrapper fails with a Maven version error

This repository expects Maven 3.9 through the wrapper. Run the wrapper from the `server` directory:

```bash
cd server
./mvnw -v
```

On Windows:

```powershell
cd server
.\mvnw.cmd -v
```

### The frontend opens but data does not load

Open the pages through the Spring Boot server, not by double-clicking HTML files:

```text
http://localhost:8080/front/index.html
http://localhost:8080/admin/dist/index.html
```

Also confirm that MySQL is running and that [db.sql](db.sql) was imported.

### Port 8080 is already in use

Run the app on another port:

```bash
java -jar target/gavel-server-2.0.0.jar --server.port=18080
```

### Docker starts but the database is not reset

Docker Compose keeps MySQL data in a named volume. Remove it when you want a fresh import:

```bash
docker compose down -v
docker compose up -d
```

## Roadmap

- Real-time outbid notifications with WebSocket.
- Scheduled auction closing and winner settlement.
- Object storage support for uploads.
- JWT or refresh-token authentication option.
- Better frontend internationalization.
- Vue 3 migration for the admin console.
- Broader integration tests around the bidding and order flows.

## Contributing

Issues and pull requests are welcome. Please read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request.

For security vulnerabilities, follow [SECURITY.md](SECURITY.md) instead of opening a public issue.

## License

This project is released under the [MIT License](LICENSE).
