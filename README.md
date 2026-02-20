# Anipia バックエンドプロジェクト

Anipiaは、アニメーションの情報を閲覧し、レビューを作成して他のユーザーと共有できるサービスです。

## ✨ 主な機能

-   **ユーザー認証:** JWTを利用したログイン機能
-   **アニメーション:** アニメーション情報の照会、検索、フィルタリング機能
-   **レビュー:** アニメーションに対するレビューの作成、照会、修正、削除機能
-   **タグ:** タグに基づいたアニメーション照会機能

## 🛠️ 技術スタック

-   **Backend:** Java 17, Spring Boot, Spring Security, MyBatis
-   **Database:** MariaDB, Redis
-   **Deployment:** Docker, AWS (S3, ECS Fargate, ECR)
-   **CI/CD:** GitHub Actions, AWS CodePipeline

## 🚀 利用開始ガイド

ローカル環境でプロジェクトをセットアップし、実行する方法です。

### 1. 前提条件

-   Java 17
-   Docker Desktop (Docker Compose を含む)

### 2. インストール及び実行

本プロジェクトは、Docker Composeを利用してデータベース（MariaDB, Redis）コンテナを起動し、その後Spring Bootアプリケーションを実行する方式を推奨しています。

```bash
# 1. データベースとRedisをコンテナで起動します
docker-compose up -d

# 2. Spring Boot アプリケーションを実行します (IDEで直接実行、または以下のコマンドを利用)
./gradlew bootRun
```

### 3. 設定

#### ⚠️ **重要：設定ファイルに関する注意**

セキュリティ上の理由（データベースの認証情報、JWTシークレットキーなど）により、`application.yaml`及び関連ファイルは`.gitignore`に含まれており、リポジトリで管理されていません。このプロジェクトは公開リポジトリであるため、機密情報が漏洩しないようにするための措置です。

プロジェクトを正常に実行するには、`src/main/resources/`ディレクトリに独自の設定ファイルを作成する必要があります。例えば、`application.yaml`という名前でファイルを作成し、以下の内容を参考にしてください。

```yaml
# src/main/resources/application.yaml
server:
  servlet:
    context-path: /api

spring:
  application:
    name: anipia
  cloud:
    aws:
      credentials:
        access-key: # AWS IAMアクセスキー
        secret-key: # AWS IAMシークレットキー
      region:
        static: ap-northeast-2
      stack:
        auto: false
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  datasource:
    url: jdbc:mariadb://localhost:3306/anipia
    username: root # docker-compose.ymlで設定したユーザー名
    password: 1234 # docker-compose.ymlで設定したパスワード
    driver-class-name: org.mariadb.jdbc.Driver
  data:
    redis:
      host: localhost
      port: 6379
  mail:
    host: smtp.gmail.com
    port: 587
    username: # ご自身のGmailアドレス
    password: # Gmailアプリのパスワード
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
  output:
    ansi:
      enabled: always

logging:
  level:
    com.afivestudio.anipia: DEBUG

mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  type-aliases-package: com.afivestudio.anipia.**.domain, com.afivestudio.anipia.**.dto
  configuration:
    map-underscore-to-camel-case: true
    default-enum-type-handler: org.apache.ibatis.type.EnumTypeHandler
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl

security:
  cors:
  allowed-origins:
    - http://localhost:5173
  whitelist:
    - pattern: /error
    # user
    - method: POST
      pattern: /users
    - method: GET
      pattern: /users/check-nickname
    - method: GET
      pattern: /users/check-email
    # auth
    - method: POST
      pattern: /auth/login
    - method: POST
      pattern: /auth/reissue
    - method: POST
      pattern: /auth/verify-email
    - method: POST
      pattern: /auth/password-reset/request
    - method: GET
      pattern: /auth/password-reset/verify
    - method: PATCH
      pattern: /auth/password-reset/confirm
    # tag
    - method: GET
      pattern: /tags
    # company
    - method: GET
      pattern: /companies
    # inquiry
    - method: POST
      pattern: /inquiries
    - method: GET
      pattern: /inquiries
    # animation
    - method: GET
      pattern: /animations
    - method: GET
      pattern: /animations/curated
    - method: GET
      pattern: /animations/**
    # review
    - pattern: /reviews/**
      method: GET
    # swagger
    - pattern: /v3/api-docs/**
    - pattern: /swagger-ui/**
    - pattern: /swagger-resources/**

  jwt:
    secret-key: # 独自のJWTシークレットキーを入力してください
    access-token-expiration: 3h
    refresh-token-expiration: 30d

app:
  user:
    default-profile-image-path: "static/defaults/user-profiles/fcfb9048-b48d-4535-beb3-8745ee9a612f.png"
  auth:
    email-verification-url: http://localhost:5173/auth/verify
    email-code-expiration: 5m
    password-reset-url: http://localhost:5173/auth/password-reset/confirm
    password-token-expiration: 5m
  file:
    s3:
      bucket: # AWS S3バケット名
```

## 🗄️ データベーススキーマ

プロジェクトの初期化に必要なデータベーススキーマは `init.sql` ファイルに含まれています。以下は、このプロジェクトのERDです。

![Anipia ERD](anipia_erd.png)

## 📄 APIエンドポイント

主要なAPIエンドポイントの詳細は、Swagger UIで確認できます。
アプリケーション実行後、 `http://localhost:8080/swagger-ui/index.html` にアクセスしてください。
