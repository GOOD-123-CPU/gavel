# ---- 构建阶段：Maven + JDK17 ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 先复制 pom 利用 Docker 层缓存加速依赖下载
COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn -q clean package -DskipTests

# ---- 运行阶段：仅 JRE ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 以非 root 运行
RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=build /app/target/*.jar app.jar

# 上传目录持久化挂载点
VOLUME ["/app/upload"]

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
