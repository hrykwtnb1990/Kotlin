2025年　12月12日（金）発表　他言語プロジェクト

### Todoリストと職場安全活動表のデジタル化
- 日常のやることリストで業務を共有する。
- 職場安全活動表をデジタル化することで、手書き書類を低減する。
- 安全に対する感度を落とさずにデジタル化を進めるにあたり、日々の記入時間を低減する。
- 安全の現地現物をこれまで同様に推進し、KYをデータとして記録に残す。

### 技術スタック

### Backend
- Kotlin
- Spring Boot 4
- Spring Data JPA + Hibernate
- PostgreSQL
- Gradle

### Frontend
- Vue 3
- Vite
- Vuetify
- Node.js / npm

### 事前準備
- [Java 21 (Amazon Corretto 21)]
- [Node.js 22]
- [PostgreSQL 14]


### 1.DB作成
- PostgreSQL上で、ローカル開発用データベースを用意する

### 2.application.yaml設定

### 3.API一覧
- GET /api/todos
- POST /api/todos
- PUT /api/todos/{id}
- DELETE /api/todos/{id}

- GET /api/notes?year=2025&month=12
- POST /api/notes
- PUT /api/notes/{id}
- DELETE /api/notes/{id}

### 4.バックエンド起動
- cd backend
- ./gradlew bootRun

### 5.フロントエンド
- 依存関係インストール　cd frontend 
- npm install

### 開発サーバー起動　npm run dev

- http://localhost:5173


### 開発計画
- Noteの検索機能
- 改善効果の数値化
- TodoとNoteを紐付ける
- 本番環境へのデプロイ

### ライセンスについて
- 社内プロトタイプのため、利用前にご連絡をお願いいたします。
