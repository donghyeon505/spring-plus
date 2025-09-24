# SPRING PLUS

Kotlin 문법 기초/심화 주차 과제

## 주요 기능

- 회원가입/로그인 및 JWT 발급
- 사용자 정보 조회, 비밀번호 변경
- 관리자 권한으로 사용자 역할 변경
- Todo 생성, 목록/상세 조회, 조건 검색
- Todo 매니저 등록/조회/삭제
- Todo 에 댓글 등록/조회

## API 명세서

**Auth**

| 기능   | 메서드  | ENDPOINT (URI) |
|------|------|----------------|
| 회원가입 | POST | /auth/signup   |
| 로그인  | POST | /auth/signin   |

**User**

| 기능      | 메서드 | ENDPOINT (URI)  |
|---------|-----|-----------------|
| 사용자 조회  | GET | /users/{userId} |
| 비밀번호 변경 | PUT | /users          |

**Admin**

| 기능        | 메서드   | ENDPOINT (URI)        |
|-----------|-------|-----------------------|
| 사용자 권한 변경 | PATCH | /admin/users/{userId} |
| 로그 열람     | GET   | /admin//logs          | 

**Todo**

| 기능            | 메서드  | ENDPOINT (URI)  |
|---------------|------|-----------------|
| Todo 등록       | POST | /todos          |
| Todo 전체 조회    | GET  | /todos          |
| Todo 단건 조회    | GET  | /todos/{todoId} |
| Todo 검색 (키워드) | GET  | /search         |

**Comment**

| 기능       | 메서드  | ENDPOINT (URI)           |
|----------|------|--------------------------|
| 댓글 등록    | POST | /todos/{todoId}/comments |
| 댓글 목록 조회 | GET  | /todos/{todoId}/comments |

**Manager**

| 기능        | 메서드    | ENDPOINT (URI)                       |
|-----------|--------|--------------------------------------|
| 매니저 등록    | POST   | /todos/{todoId}/managers             |
| 매니저 목록 조회 | GET    | /todos/{todoId}/managers             |
| 매니저 삭제    | DELETE | /todos/{todoId}/managers/{managerId} |

### 요청 / 응답

<details>
  <summary>Auth</summary>

### 회원가입

요청 POST /auth/singup

```json
{
  "email": "test@test.com",
  "password": "test",
  "nickname": "test",
  "userRole": "USER"
}
```

응답 (200 OK)

```json
{
  "bearerToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJ0ZXN0QHRlc3QuY29tIiwibmlja25hbWUiOiJ0ZXN0IiwidXNlclJvbGUiOiJVU0VSIiwiZXhwIjoxNzU4Njc4MDI1LCJpYXQiOjE3NTg2NzQ0MjV9.DES6fvJVN7YezYMHobdZsCwh77M_P9W7HDvEDxiCBeQ"
}
```

### 로그인

요청 POST /auth/singin

```json
{
  "email": "test@test.com",
  "password": "test"
}
```

응답 (200 OK)

```json
{
  "bearerToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJ0ZXN0QHRlc3QuY29tIiwibmlja25hbWUiOiJ0ZXN0IiwidXNlclJvbGUiOiJVU0VSIiwiZXhwIjoxNzU4Njc4MTMzLCJpYXQiOjE3NTg2NzQ1MzN9.NJcT8exwIeVp8pRtsLVDUc0DPkdy0sFhR9NKcXBJ8h8"
}
```

</details>

<details>
  <summary>User</summary>

### 사용자 정보 조회

요청 GET /users/{userId}

응답 (200 OK)

```json
{
  "id": 4,
  "email": "test@test.com"
}
```

### 비밀번호 변경

요청 PUT /users

```json
{
  "oldPassword": "test",
  "newPassword": "test1234A"
}
```

응답 (200 OK)

</details>

<details>
  <summary>Admin</summary>

### 권한 변경

요청 PATCH /admin/users/{userId}

```json
{
  "role": "ADMIN"
}
```

응답 (200 OK)

### 로그 조회

| 파라미터 | 타입  | 설명           | default |
|------|-----|--------------|---------|
| page | int | 페이지 번호       | 0       |
| size | int | 페이지 당 log 갯수 | 10      |

요청 GET /admin/logs

응답 (200 OK)

```json
{
  "content": [
    {
      "requesterId": 3,
      "targetManagerId": 2,
      "success": true,
      "createdAt": "2025-09-22T15:49:37.05618"
    },
    {
      "requesterId": 2,
      "targetManagerId": 2,
      "success": false,
      "createdAt": "2025-09-22T15:49:16.046391"
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 2,
    "totalPages": 1
  }
}
```

</details>

<details>
  <summary>Todo</summary>

### 할일 저장

요청 POST /todos

```json
{
  "title": "테스트",
  "contents": "test content"
}
```

응답 (200 OK)

```json
{
  "id": 12,
  "title": "테스트",
  "contents": "test content",
  "weather": "Light Snow and Fog",
  "user": {
    "id": 3,
    "email": "test4@test.com"
  }
}
```

### 할일 전체 조회

**request Param**

| 파라미터      | 타입             | 설명               | default | Nullable |
|-----------|----------------|------------------|---------|----------|
| page      | int            | 페이지 번호           | 0       | O        |
| size      | int            | 페이지 당 todo 갯수    | 10      | O        |
| direction | Sort.Direction | 페이지 정렬 기준        | DESC    | O        | 
| weather   | String         | 검색 키워드 (날씨)      |         | O        |
| startDate | LocalDate      | 검색 할 범위 (시작 날짜)  |         | O        |
| endDate   | LocalDate      | 검색 할 범위 (끝나는 날짜) |         | O        |

요청 GET /todos?param=...

응답 (200 OK)

```json
{
  "content": [
    {
      "id": 1,
      "title": "test title",
      "contents": "test content",
      "weather": "Heavy Snow",
      "user": {
        "id": 1,
        "email": "test2@test.com"
      },
      "createdAt": "2025-09-17T14:47:02.016871",
      "modifiedAt": "2025-09-17T14:47:02.016871"
    },
    {
      "id": 2,
      "title": "test title",
      "contents": "test content",
      "weather": "Heavy Snow",
      "user": {
        "id": 1,
        "email": "test2@test.com"
      },
      "createdAt": "2025-09-17T16:42:41.23357",
      "modifiedAt": "2025-09-17T16:42:41.23357"
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 2,
    "totalPages": 1
  }
}
```

### 할일 단건 조회

요청 GET /todos/1

응답 (200 OK)

```json
{
  "id": 1,
  "title": "test title",
  "contents": "test content",
  "weather": "Heavy Snow",
  "user": {
    "id": 3,
    "email": "test4@test.com"
  },
  "createdAt": "2025-09-17T14:47:02.016871",
  "modifiedAt": "2025-09-17T14:47:02.016871"
}
```

### 할일 검색

**request Param**

| 파라미터            | 타입        | 설명               | default | Nullable |
|-----------------|-----------|------------------|---------|----------|
| page            | int       | 페이지 번호           | 0       | O        |
| size            | int       | 페이지 당 todo 갯수    | 10      | O        |
| startDate       | LocalDate | 검색 할 범위 (시작 날짜)  |         | O        | 
| endDate         | LocalDate | 검색 할 범위 (끝나는 날짜) |         | O        |
| keyword         | String    | 검색 키워드 (제목)      |         | O        |
| managerNickname | String    | 검색 키워드 (매니저 이름)  |         | O        |

**Keyword 와 managerNickname 중 하나는 필수**

요청 GET /search?keyword=테스

응답 (200 OK)

```json
{
  "content": [
    {
      "title": "테스트1",
      "managerCount": 2,
      "commentCount": 0
    },
    {
      "title": "테스트",
      "managerCount": 1,
      "commentCount": 0
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 2,
    "totalPages": 1
  }
}
```

</details>

<details>
  <summary>Comment</summary>

### 댓글 달기

요청 POST /todos/1/comments

```json
{
  "contents": "test comment2"
}
```

응답 (200 OK)

```json
{
  "id": 2,
  "contents": "test comment2",
  "user": {
    "id": 3,
    "email": "test4@test.com"
  }
}
```

### 댓글 검색

요청 GET /todos/1/comments

응답 (200 OK)

```json
[
  {
    "id": 1,
    "contents": "test comment",
    "user": {
      "id": 1,
      "email": "test2@test.com"
    }
  },
  {
    "id": 2,
    "contents": "test comment2",
    "user": {
      "id": 3,
      "email": "test4@test.com"
    }
  }
]
```

</details>

<details>
  <summary>Manager</summary>

요청 POST /todos/14/managers

```json
{
  "managerUserId": 2
}
```

응답 (200 OK)

```json
{
  "id": 18,
  "user": {
    "id": 2,
    "email": "test3@test.com"
  }
}
```

요청 POST /todos/14/managers

응답 (200 OK)

```json
[
  {
    "id": 17,
    "user": {
      "id": 3,
      "email": "test4@test.com"
    }
  },
  {
    "id": 18,
    "user": {
      "id": 2,
      "email": "test3@test.com"
    }
  }
]
```

요청 POST /todos/14/managers/18

응답 (200 OK)

</details>

