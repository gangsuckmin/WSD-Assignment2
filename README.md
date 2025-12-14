# WSD Assignment2
- 웹서비스설계 과제2

---

## 프로젝트 개요 (문제정의, 주요 기능 목록)

### 문제 정의
과제 1에서 설계한 데이터베이스 스키마(ERD/명세서)를 기반으로,
Book / Review / Comment / Order 등 핵심 도메인에 대한 REST API 서버를 구현하고
JCloud 환경에 배포하여 실제 동작 여부를 검증한다.

### 주요 기능 목록
- JWT 기반 인증/인가 (Access Token / Refresh Token)
- ROLE_USER / ROLE_ADMIN 권한 분리
- Book / Review / Comment / Order CRUD API
- 페이지네이션, 정렬, 검색 기능
- Swagger(OpenAPI) 자동 문서화
- JCloud 서버 배포 및 헬스체크 제공

---

## 실행 방법

### 로컬 실행

#### 의존성 설치 및 빌드
```bash
./gradlew clean build
```

#### 서버 실행
```bash
java -jar build/libs/assignment2-0.0.1-SNAPSHOT.jar
```

- 기본 포트: 8080
- Swagger: http://localhost:8080/swagger-ui/index.html

---

## 환경변수 설명 (.env.example)

```env
# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=assignment2
DB_USERNAME=appuser
DB_PASSWORD=********

# JWT
JWT_SECRET=your-secret-key
JWT_ACCESS_EXPIRE=3600
JWT_REFRESH_EXPIRE=604800
```

- 실제 값은 `.env` 파일에 저장하며 GitHub에는 업로드하지 않는다.

---

## 배포 주소

- Base URL  
http://113.198.66.68:10083

- Swagger URL  
http://113.198.66.68:10083/swagger-ui/index.html

- Health URL  
http://113.198.66.68:10083/health

---

## 인증 플로우 설명

1. `POST /auth/login` 요청
2. Access Token / Refresh Token 발급
3. 인증이 필요한 API 요청 시 Header에 Access Token 포함

```
Authorization: Bearer {accessToken}
```

4. Access Token 만료 시 `POST /auth/refresh` 호출

---

## 역할 / 권한표 (ROLE_USER / ROLE_ADMIN)

| Role | 설명 | 접근 가능 API |
|------|------|---------------|
| ROLE_USER | 일반 사용자 | 도서 조회, 리뷰/댓글 작성 및 조회 |
| ROLE_ADMIN | 관리자 | 사용자 관리, 관리자 전용 API |

---

## 예제 계정

- 일반 사용자  
`user1@example.com / P@ssw0rd!`

- 관리자  
`admin@example.com / P@ssw0rd!`  
(관리자 계정은 관리자 전용 API 접근 가능)

---

## DB 연결 정보 (테스트용)

- Host: 113.198.66.68
- Port: 3306
- Database: assignment2
- Username: appuser
- Password: 별도 제출 파일에 명시
- 권한 범위: 해당 데이터베이스 접근 권한만 부여

---

## 엔드포인트 요약표

| Method | URL | 설명 |
|--------|-----|------|
| POST | /auth/login | 로그인 |
| POST | /auth/refresh | 토큰 재발급 |
| GET | /health | 헬스체크 |
| GET | /books | 도서 목록 조회 |
| GET | /books/{id} | 도서 상세 조회 |
| POST | /reviews | 리뷰 작성 |
| GET | /reviews | 리뷰 목록 조회 |
| POST | /comments | 댓글 작성 |
| GET | /comments | 댓글 조회 |
| POST | /orders | 주문 생성 |
| GET | /orders | 주문 조회 |

---

## 성능 / 보안 고려사항
- JWT 기반 Stateless 인증 구조
- 비밀번호 BCrypt 해시 저장
- 검색/정렬 컬럼 MySQL 인덱스 적용
- CORS 설정 적용
- 민감 정보(.env, key 파일) GitHub 업로드 금지

---

## 한계와 개선 계획

### 한계
- 자동화 테스트 코드 부족
- 관리자 전용 기능 제한
- 대용량 데이터 성능 검증 미흡

### 개선 계획
- 통계/집계 API 추가
- 관리자 기능 확장
- 통합 테스트 및 CI 파이프라인 구축
- 캐싱(Redis) 도입 검토