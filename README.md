# WSD Assignment2(Term project)
- 웹서비스설계 과제2(텀 프로젝트)

---

## 프로젝트 개요

### 문제 정의
과제 1에서 설계한 데이터베이스 스키마(ERD/명세서)를 기반으로  
Book / Review / Comment / Order 등 핵심 도메인에 대한 REST API 서버를 구현하고,  
Docker 기반으로 JCloud 환경에 배포하여 실제 서비스 수준의 동작을 검증한다.

본 프로젝트는 **Swagger 접속 가능**, **Health Check 200 응답**,  
그리고 **서버 재부팅 이후에도 서비스가 지속 구동**됨을 확인하는 것을 목표로 한다.

### 주요 기능 목록
- JWT 기반 인증/인가 (Access Token / Refresh Token)
- ROLE_USER / ROLE_ADMIN 권한 분리
- Book / Review / Comment / Order CRUD API
- 페이지네이션, 정렬, 검색 기능
- Swagger(OpenAPI) 자동 문서화
- Docker + docker-compose 기반 배포
- JCloud 서버 배포 및 헬스체크 제공
- 서버 재부팅 이후 컨테이너 자동 재기동 확인

---

## 실행 방법

## 1) 로컬 실행

### 빌드
```bash
./gradlew clean build
```

### 실행
```bash
java -jar build/libs/assignment2-0.0.1-SNAPSHOT.jar
```

- 기본 포트: 8080  
- Swagger: http://localhost:8080/swagger-ui/index.html  
- Health: http://localhost:8080/actuator/health  

---

## 2) Docker 실행 (로컬 / 서버 공통)

### jar 빌드 및 컨테이너 실행
```bash
./gradlew clean bootJar
docker compose up -d --build
docker ps
```

### 중지
```bash
docker compose down
```

---

## 3) 테스트 실행

```bash
./gradlew test
```

- 기본 단위 테스트 및 컨트롤러 테스트를 포함한다.

## 환경변수 설명 (.env.example)

```env
# Database
DB_HOST=mysql
DB_PORT=3306
DB_NAME=assignment2
DB_USERNAME=assignment2
DB_PASSWORD=********
MYSQL_ROOT_PASSWORD=********

# JWT
JWT_SECRET=change-me
JWT_ACCESS_EXPIRES_MIN=15
JWT_REFRESH_EXPIRES_DAYS=14
```

- 실제 값은 `.env` 파일에 저장하며 GitHub에는 업로드하지 않는다.
- Public Repository에는 `.env.example`만 포함한다.

---

## 배포 주소 (JCloud)

- Base URL  
http://113.198.66.68:10083

- Swagger  
http://113.198.66.68:10083/swagger-ui/index.html

- Health  
http://113.198.66.68:10083/actuator/health

- Postman 컬렉션 및 환경 설정 파일은 `postman/` 디렉토리에 포함되어 있으며,
  이를 통해 인증/인가 및 주요 API 호출을 바로 테스트할 수 있다.

---

## 인증 플로우

1. `POST /auth/login` 요청  
2. Access Token / Refresh Token 발급  
3. 인증이 필요한 API 요청 시 Header에 Access Token 포함  

```
Authorization: Bearer {accessToken}
```

4. Access Token 만료 시 `POST /auth/refresh` 호출  

---

## 역할 / 권한

| Role | 설명 | 접근 가능 |
|------|------|----------|
| ROLE_USER | 일반 사용자 | 도서 조회, 리뷰/댓글/장바구니/주문 |
| ROLE_ADMIN | 관리자 | 사용자 관리, 도서 관리 등 |

---

## 예제 계정
(시드 데이터 기준)

- 일반 사용자  
`user1@example.com / P@ssw0rd!`

- 관리자  
`admin@example.com / P@ssw0rd!`

---

## DB 연결 정보

- DBMS: MySQL 8.0 (Docker Container)
- Database: assignment2
- User: assignment2
- Password: 별도 제출 파일에 명시
- JCloud 서버 내부 접근 전용

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
