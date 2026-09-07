# Changelog

All notable changes to **Gavel** are documented here.
Format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/); versioning: [SemVer](https://semver.org/).

## [2.0.0] — 2026-09-06

### Project rename
- Project rebranded from a course-design codebase to **Gavel** — Open-source Online Auction Platform.
- Java package layout migrated to `com.gavel.*`; main class renamed to `GavelApplication`.
- Maven coordinates: `dev.gavel:gavel-server:2.0.0`.
- Frontend portals rebranded (page titles, project name).

### API
- REST routes renamed to English resource paths; the noisy servlet context-path was removed:
  | Old (v1) | New (v2) |
  |---|---|
  | `/springbootp0eo6/paimaishangpin` | `/auction-items` |
  | `/springbootp0eo6/jingpaidingdan` | `/orders` |
  | `/springbootp0eo6/lishijingpai` | `/bid-history` |
  | `/springbootp0eo6/shangpinleixing` | `/categories` |
  | `/springbootp0eo6/yonghu` | `/members` |
  | `/springbootp0eo6/users` | `/users` |
  | `/springbootp0eo6/news` | `/news` |
  | `/springbootp0eo6/messages` | `/messages` |
- Interactive API docs via springdoc-openapi: `GET /swagger-ui.html`.
- Health endpoint via Spring Boot Actuator: `GET /actuator/health`.

### Platform
- Spring Boot `2.2.2` → `3.5.5` (Java 17, jakarta namespace).
- MyBatis-Plus `2.3` → `3.5.9` (with `mybatis-plus-jsqlparser`).

### Security (since 1.x audit)
- Passwords hashed with BCrypt; legacy plaintext upgraded transparently on first login.
- Dynamic-query endpoints restricted by table/column whitelist; `/cal`, `/group`, `/value`, `/remind`, `/sh`, `matchFace`, `location` removed.
- File upload hardened: extension whitelist, UUID renaming, path-traversal protection.
- CORS moved from "echo any origin + credentials" to a configured whitelist.
- Global exception handler; no stack traces leak to clients.
- Database seed scrubbed of real-looking PII; all demo identities are fictional.

### Infrastructure
- Multi-stage `Dockerfile`, `docker-compose.yml` (MySQL 8 + app, auto-import of `db.sql`).
- GitHub Actions CI: build on push/PR.
- `Makefile`, `.env.example`, English + Chinese READMEs.
- Database renamed to `gavel`; schema in utf8mb4.

### Breaking changes
- All endpoint paths changed (see table above); clients must drop the context-path prefix.
- Default database name is now `gavel`.
- Java 17+ required.
