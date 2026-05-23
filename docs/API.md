# API 接口文档

> 学生信息管理系统 — 后端接口文档
>
> 基础 URL：`http://localhost:8080`

---

## 一、通用说明

### 统一响应格式

所有接口返回 JSON，结构如下：

```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

| code | 含义 |
|------|------|
| 200 | 请求成功 |
| 400 | 请求参数错误 |
| 401 | 未认证（Token 缺失或无效） |
| 500 | 服务器内部错误 |

### 认证方式

除认证模块外，所有接口都需要在请求头携带 JWT Token：

```
Authorization: Bearer <token>
```

Token 通过登录接口获取，有效期 24 小时。

### 分页规范

需要分页的接口使用以下参数：

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| page | int | 1 | 页码，从 1 开始 |
| pageSize | int | 10 | 每页条数 |

> 前端分页时建议同时调用 `/count` 接口获取总数，用于计算总页数。

---

## 二、认证模块

所有接口公开访问，不需要 Token。

### 接口概览

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/login` | 用户登录 |
| POST | `/api/auth/register` | 用户注册 |

### POST /api/auth/login — 登录

**请求体：**

```json
{
  "username": "admin",
  "password": "123456"
}
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
```

**失败响应：**

```json
{
  "code": 401,
  "message": "用户名或密码错误",
  "data": null
}
```

### POST /api/auth/register — 注册

**请求体：**

```json
{
  "username": "admin",
  "password": "123456"
}
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

---

## 三、学生管理

所有接口需要 Token。

### 接口概览

| 方法 | 路径 | Query/Body 参数 | 说明 |
|------|------|-----------------|------|
| GET | `/api/students` | `page`, `pageSize`, `name?`, `studentNumber?` | 分页查询，支持姓名/学号模糊搜索 |
| GET | `/api/students/count` | `name?`, `studentNumber?` | 获取符合条件的总数 |
| GET | `/api/students/{id}` | — | 根据 ID 查询 |
| POST | `/api/students` | Body: Student JSON | 新增学生 |
| PUT | `/api/students/{id}` | Body: Student JSON | 更新学生 |
| DELETE | `/api/students/{id}` | — | 删除学生 |
| DELETE | `/api/students/batch` | Body: `[1, 2, 3]` | 批量删除 |
| POST | `/api/students/{id}/avatar` | FormData: `file` | 上传头像 |

### GET /api/students — 分页查询

```
GET /api/students?page=1&pageSize=10&name=张
```

**响应 data 示例：**

```json
[
  {
    "id": 1,
    "studentNumber": "2024001",
    "name": "张三",
    "gender": "男",
    "className": "计算机科学2024-1",
    "phone": "13800138000",
    "email": "zhangsan@example.com",
    "avatar": "/uploads/abc.jpg"
  }
]
```

### POST /api/students/{id}/avatar — 上传头像

```
POST /api/students/1/avatar
Content-Type: multipart/form-data

表单字段：file（文件）
文件大小限制：2MB 以内
```

**成功响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": "/uploads/a1b2c3d4.jpg"
}
```

返回的路径可直接拼接为完整 URL：`http://localhost:8080/uploads/a1b2c3d4.jpg`

### 请求体示例（POST / PUT）

```json
{
  "studentNumber": "2024001",
  "name": "张三",
  "gender": "男",
  "className": "计算机科学2024-1",
  "phone": "13800138000",
  "email": "zhangsan@example.com"
}
```

> 新增和更新时，`studentNumber` 和 `name` 为必填项，`email` 需符合邮箱格式。校验失败返回 `{ "code": 400, "message": "xxx不能为空" }`。

### 批量删除请求体

```json
[1, 2, 3, 5, 8]
```

---

## 四、课程管理

所有接口需要 Token。

### 接口概览

| 方法 | 路径 | Query/Body 参数 | 说明 |
|------|------|-----------------|------|
| POST | `/api/courses` | Body: Course JSON | 新增课程 |
| PUT | `/api/courses` | Body: Course JSON | 更新课程 |
| DELETE | `/api/courses/{id}` | — | 删除课程 |
| DELETE | `/api/courses/batch` | Body: `[1, 2]` | 批量删除 |
| GET | `/api/courses/{id}` | — | 根据 ID 查询 |
| GET | `/api/courses/number/{courseNumber}` | — | 根据课程编号查询 |
| GET | `/api/courses/list` | — | 查询全部 |
| GET | `/api/courses/search` | `keyword?` | 按关键词搜索 |
| GET | `/api/courses/page` | `offset`, `size` | 分页查询（偏移量方式） |
| GET | `/api/courses/count` | — | 查询总数 |
| GET | `/api/courses/semester/{semester}` | — | 按学期查询 |
| GET | `/api/courses/teacher/{teacher}` | — | 按教师查询 |

### 请求体示例（POST / PUT）

```json
{
  "courseName": "Java 程序设计",
  "courseNumber": "CS201",
  "credit": 3.5,
  "teacher": "王教授",
  "semester": 1
}
```

### GET /api/courses/page — 分页查询

```
GET /api/courses/page?offset=0&size=10
```

> 此接口使用 `offset` + `size` 方式（而非 `page` + `pageSize`），前端注意区分。

---

## 五、成绩管理

所有接口需要 Token。查询类接口通过三表 JOIN 返回完整的关联信息（含学生姓名、学号、课程名称、学分、教师、学期）。

### 接口概览

#### CRUD

| 方法 | 路径 | 参数 | 说明 |
|------|------|------|------|
| POST | `/api/scores` | Body: `{ studentId, courseId, score }` | 录入成绩 |
| PUT | `/api/scores` | Body: `{ id, score }` | 修改成绩 |
| DELETE | `/api/scores/{id}` | — | 按 ID 删除 |
| DELETE | `/api/scores/student/{studentId}` | — | 删除某学生全部成绩 |
| DELETE | `/api/scores/course/{courseId}` | — | 删除某课程全部成绩 |
| GET | `/api/scores/{id}` | — | 按 ID 查询 |

#### 查询

| 方法 | 路径 | 参数 | 说明 |
|------|------|------|------|
| GET | `/api/scores/search` | `studentId?`, `courseId?` | 组合查询（两个参数均可不传） |
| GET | `/api/scores/student/{studentId}` | — | 查询某学生全部成绩 |
| GET | `/api/scores/course/{courseId}` | — | 查询某课程全部成绩 |
| GET | `/api/scores/check` | `studentId`, `courseId` | 检查成绩是否已存在 |
| GET | `/api/scores/full` | `studentId`, `courseId` | 查询学生某课程完整成绩信息 |

#### 统计分析

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/scores/course/{courseId}/avg` | 课程平均分 |
| GET | `/api/scores/course/{courseId}/max` | 课程最高分 |
| GET | `/api/scores/course/{courseId}/min` | 课程最低分 |
| GET | `/api/scores/course/{courseId}/passRate` | 课程及格率（百分比） |
| GET | `/api/scores/course/{courseId}/excellentRate` | 课程优秀率（≥80分） |
| GET | `/api/scores/course/{courseId}/distribution` | 课程分数段分布 |
| GET | `/api/scores/student/{studentId}/avg` | 学生平均分 |
| GET | `/api/scores/student/{studentId}/statistics` | 学生成绩综合统计 |

### GET /api/scores/search — 组合查询

```
GET /api/scores/search?studentId=1&courseId=3    # 两项都传
GET /api/scores/search?studentId=1                # 只按学生查
GET /api/scores/search?courseId=3                 # 只按课程查
GET /api/scores/search                            # 不传查询全部
```

**响应 data 示例（单条）：**

```json
{
  "id": 1,
  "studentId": 1,
  "courseId": 3,
  "score": 85.5,
  "studentName": "张三",
  "studentNumber": "2024001",
  "courseName": "Java 程序设计",
  "courseNumber": "CS201",
  "credit": 3.5,
  "teacher": "王教授",
  "semester": 1
}
```

### GET /api/scores/course/{courseId}/distribution — 分数段分布

**响应 example：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "excellent": 5,
    "good": 12,
    "medium": 8,
    "pass": 3,
    "fail": 2
  }
}
```

| 字段 | 含义 |
|------|------|
| excellent | ≥ 90 分 |
| good | 80 ~ 89 分 |
| medium | 70 ~ 79 分 |
| pass | 60 ~ 69 分 |
| fail | < 60 分 |

### GET /api/scores/student/{studentId}/statistics — 学生综合统计

**响应 example：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "courseCount": 6,
    "totalScore": 510.0,
    "avgScore": 85.0,
    "maxScore": 95.0,
    "minScore": 72.0
  }
}
```

---

## 六、用户管理

所有接口需要 Token。

| 方法 | 路径 | 参数 | 说明 |
|------|------|------|------|
| GET | `/api/users/{id}` | — | 根据 ID 查询 |
| GET | `/api/users/username/{username}` | — | 根据用户名查询 |
| POST | `/api/users` | Body: User JSON | 新增用户（密码自动加密） |
| PUT | `/api/users/password` | Body: `{ id, password }` | 修改密码 |

---
*文档更新时间：2026-05-26*
