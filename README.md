# WSD Assignment2
- 웹서비스설계 과제2

---

## 프로젝트 개요

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
DB_USERNAME=assignment2
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
- Port: 19083
- Database: assignment2
- Username: assignment2
- Password: 별도 제출 파일에 명시
- 권한 범위: 해당 데이터베이스 접근 권한만 부여

---

## 엔드포인트 요약표

| No | Method | URL | 설명 | 권한 |
|----|--------|-----|------|------|
| 1 | POST | /auth/login | 로그인 (Access / Refresh Token 발급) | ALL |
| 2 | POST | /auth/refresh | Access Token 재발급 | ALL |
| 3 | POST | /auth/logout | 로그아웃 | USER |
| 4 | GET | /users/me | 내 정보 조회 | USER |
| 5 | PATCH | /users/me | 내 정보 수정 | USER |
| 6 | GET | /users | 사용자 목록 조회 | ADMIN |
| 7 | PATCH | /users/{id}/deactivate | 사용자 비활성화 | ADMIN |
| 8 | POST | /books | 도서 등록 | ADMIN |
| 9 | GET | /books | 도서 목록 조회 (페이지네이션/정렬/검색) | ALL |
| 10 | GET | /books/{id} | 도서 상세 조회 | ALL |
| 11 | PATCH | /books/{id} | 도서 정보 수정 | ADMIN |
| 12 | DELETE | /books/{id} | 도서 삭제 | ADMIN |
| 13 | POST | /reviews | 리뷰 작성 | USER |
| 14 | GET | /reviews | 리뷰 목록 조회 | ALL |
| 15 | GET | /reviews/{id} | 리뷰 상세 조회 | ALL |
| 16 | PATCH | /reviews/{id} | 리뷰 수정 | USER |
| 17 | DELETE | /reviews/{id} | 리뷰 삭제 | USER |
| 18 | POST | /reviews/{id}/like | 리뷰 좋아요 | USER |
| 19 | DELETE | /reviews/{id}/like | 리뷰 좋아요 취소 | USER |
| 20 | POST | /comments | 댓글 작성 | USER |
| 21 | GET | /comments | 댓글 목록 조회 | ALL |
| 22 | PATCH | /comments/{id} | 댓글 수정 | USER |
| 23 | DELETE | /comments/{id} | 댓글 삭제 | USER |
| 24 | POST | /comments/{id}/like | 댓글 좋아요 | USER |
| 25 | DELETE | /comments/{id}/like | 댓글 좋아요 취소 | USER |
| 26 | POST | /cart/items | 장바구니에 도서 추가 | USER |
| 27 | GET | /cart | 내 장바구니 조회 | USER |
| 28 | PATCH | /cart/items/{id} | 장바구니 수량 변경 | USER |
| 29 | DELETE | /cart/items/{id} | 장바구니 항목 삭제 | USER |
| 30 | DELETE | /cart/clear | 장바구니 비우기 | USER |
| 31 | POST | /orders | 주문 생성 | USER |
| 32 | GET | /orders | 내 주문 목록 조회 | USER |
| 33 | GET | /orders/{id} | 주문 상세 조회 | USER |
| 34 | PATCH | /orders/{id}/cancel | 주문 취소 | USER |
| 35 | GET | /users/me/likes | 내가 누른 좋아요 목록 조회 | USER |

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