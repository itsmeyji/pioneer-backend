#  Pioneer Project

##  프로젝트 소개
Pioneer Project는 **연구실 운영 효율화**를 목표로 하는 웹 서비스
회원 관리, 출석, 과제 제출, 일정 관리 기능을 제공하여 **연구실 운영의 디지털 전환**을 지원  
(당 프로젝트의 백엔드 코드를 작성함)

---

##  주요 기능
- **회원 관리**: 회원가입, 로그인, 권한 관리  
- **출석 관리**: 출석 현황 기록 및 조회  
- **과제 관리**: 과제 생성, 제출 현황 조회  
- **일정 관리**: 연구실 일정 등록 및 확인  

---

## 🛠️ 기술 스택
- **Frontend**: React, Axios (Port 3000)  
- **Backend**: Spring Boot, Gradle, JPA, Lombok, Spring Security (Port 8080)  
- **Database**: MySQL  
- **협업/배포**: GitHub  

---

## 📡 API 명세

### 🔑 인증 / 회원
| 기능             | Method | Endpoint              | 설명 |
|------------------|--------|-----------------------|------|
| 회원가입         | POST   | `/api/auth/signup`    | 신규 회원 등록 |
| 로그인           | POST   | `/api/auth/login`     | 사용자 로그인 |
| 로그아웃         | POST   | `/api/auth/logout`    | 사용자 로그아웃 |
| 내 이름 조회     | GET    | `/api/auth/me/name`   | 로그인한 회원 이름 반환 |

---

### 👥 사용자 / 멤버
| 기능             | Method | Endpoint                | 설명 |
|------------------|--------|-------------------------|------|
| 회원 목록 조회   | GET    | `/api/users`            | 전체 회원 목록 조회 (관리자) |
| 회원 삭제        | DELETE | `/api/users/{userId}`   | 특정 회원 삭제 (관리자) |
| 연구실 멤버 조회 | GET    | `/api/members`          | 학번/이름/학과/직책만 반환 |

---

### 📅 일정
| 기능             | Method | Endpoint             | 설명 |
|------------------|--------|----------------------|------|
| 일정 목록 조회   | GET    | `/api/schedules`     | 일정 전체 조회 |

---

### 📝 과제
| 기능             | Method | Endpoint              | 설명 |
|------------------|--------|-----------------------|------|
| 과제 목록 조회   | GET    | `/api/assignments`    | 과제 목록 조회 |
| 과제 등록        | POST   | `/api/assignments`    | 새 과제 등록 |
| 제출 현황 조회   | GET    | `/api/submissions`    | 제출 데이터 조회 |
