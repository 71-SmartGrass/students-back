# 学生信息管理系统 — 后端

基于 Spring Boot 4.0 构建的 RESTful API 后端服务，覆盖学生、课程、成绩管理与统计分析，采用 JWT + BCrypt 无状态认证方案。

## 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 4.0.6 | 应用框架 |
| Spring Security | — | 认证与授权 |
| MyBatis | 4.0.1 | ORM / 数据持久层 |
| MySQL | 8.0+ | 关系型数据库 |
| JJWT | 0.12.6 | JWT 签发与校验 |
| BCrypt | — | 密码哈希 |
| Jakarta Validation | — | 请求参数校验 |
| Maven | 3.8+ | 构建工具 |
| Java | 17+ | 运行环境 |

## 项目结构

```
src/main/java/com/example/studentsback/
├── common/
│   └── Result.java                  # 统一响应体 Result<T>
├── config/
│   ├── SecurityConfig.java          # Spring Security 配置
│   ├── JwtAuthenticationFilter.java # JWT 请求过滤器
│   ├── CorsConfig.java              # 跨域配置
│   └── WebMvcConfig.java            # 静态资源映射
├── controller/
│   ├── AuthController.java          # /api/auth/*
│   ├── StudentController.java       # /api/students/*
│   ├── CourseController.java        # /api/courses/*
│   ├── ScoreController.java         # /api/scores/*
│   └── UserController.java          # /api/users/*
├── service/
│   ├── impl/                        # 业务逻辑实现
│   └── *.java                       # 服务接口
├── mapper/                          # MyBatis 数据访问层
├── model/entity/                    # 数据实体 (Student, Course, Score, User)
├── exception/                       # 全局异常处理
└── util/                            # JWT 工具类

src/test/java/com/example/studentsback/controller/
├── AuthControllerTest.java          # 认证接口测试 (4)
├── StudentControllerTest.java       # 学生接口测试 (7)
├── CourseControllerTest.java        # 课程接口测试 (8)
├── ScoreControllerTest.java         # 成绩接口测试 (11)
└── UserControllerTest.java          # 用户接口测试 (3)
```

## 快速启动

### 前置条件

- JDK 17+
- MySQL 8.0+
- Maven 3.8+

### 1. 创建数据库并初始化表结构

```sql
CREATE DATABASE students DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE students;

CREATE TABLE users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  DEFAULT 'user',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE student (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    student_number VARCHAR(50)  NOT NULL UNIQUE,
    name           VARCHAR(50)  NOT NULL UNIQUE,
    gender         VARCHAR(10),
    class_name     VARCHAR(100),
    phone          VARCHAR(20),
    email          VARCHAR(100),
    avatar         VARCHAR(255),
    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE course (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    course_name   VARCHAR(100) NOT NULL,
    course_number VARCHAR(50)  NOT NULL UNIQUE,
    credit        DOUBLE,
    teacher       VARCHAR(50),
    semester      INT,
    create_time   DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE score (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    student_id  INT NOT NULL,
    course_id   INT NOT NULL,
    score       DOUBLE CHECK (score >= 0 AND score <= 100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id)  REFERENCES course(id)  ON DELETE CASCADE,
    UNIQUE (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### 2. 配置数据库连接

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/students?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码

jwt:
  secret: 替换为长度至少32位的随机字符串
```

### 3. 启动

```bash
mvn spring-boot:run
```

服务启动后访问 `http://localhost:8080`。

## API 参考

所有接口统一返回格式：

```json
{ "code": 200, "message": "success", "data": { } }
```

### 认证

| 方法 | 端点 | 认证 | 说明 |
|------|------|------|------|
| `POST` | `/api/auth/register` | 否 | 用户注册 |
| `POST` | `/api/auth/login` | 否 | 登录，返回 JWT Token |

注册/登录请求体：`{ "username": "...", "password": "..." }`

登录成功后返回 `data.token`，后续请求在 Header 中携带 `Authorization: Bearer <token>`。

### 学生管理

| 方法 | 端点 | 说明 |
|------|------|------|
| `GET` | `/api/students?page=1&pageSize=10&name=&studentNumber=` | 分页 + 模糊搜索 |
| `GET` | `/api/students/count?name=&studentNumber=` | 符合条件的总数 |
| `GET` | `/api/students/{id}` | 按 ID 查询 |
| `POST` | `/api/students` | 新增学生 |
| `PUT` | `/api/students/{id}` | 更新学生 |
| `DELETE` | `/api/students/{id}` | 删除学生 |
| `DELETE` | `/api/students/batch` | 批量删除 `[1, 2, 3]` |
| `POST` | `/api/students/{id}/avatar` | 上传头像 (multipart, ≤2MB) |

### 课程管理

| 方法 | 端点 | 说明 |
|------|------|------|
| `GET` | `/api/courses/list` | 查询全部 |
| `GET` | `/api/courses/page?offset=0&size=10` | 分页查询 |
| `GET` | `/api/courses/count` | 总数 |
| `GET` | `/api/courses/search?keyword=` | 关键词搜索（课程名/教师/编号） |
| `GET` | `/api/courses/semester/{semester}` | 按学期筛选 |
| `GET` | `/api/courses/teacher/{teacher}` | 按教师筛选 |
| `GET` | `/api/courses/number/{courseNumber}` | 按编号查询 |
| `GET` | `/api/courses/{id}` | 按 ID 查询 |
| `POST` | `/api/courses` | 新增课程 |
| `PUT` | `/api/courses` | 更新课程 |
| `DELETE` | `/api/courses/{id}` | 删除课程 |
| `DELETE` | `/api/courses/batch` | 批量删除 |

### 成绩管理

基础 CRUD：

| 方法 | 端点 | 说明 |
|------|------|------|
| `GET` | `/api/scores/{id}` | 按 ID 查询 |
| `GET` | `/api/scores/student/{studentId}` | 某学生全部成绩（含课程信息） |
| `GET` | `/api/scores/course/{courseId}` | 某课程全部成绩（含学生信息） |
| `GET` | `/api/scores/search?studentId=&courseId=` | 组合筛选（两参数均可选） |
| `GET` | `/api/scores/check?studentId=&courseId=` | 检查是否已有成绩记录 |
| `POST` | `/api/scores` | 新增成绩 |
| `PUT` | `/api/scores` | 更新成绩 |
| `DELETE` | `/api/scores/{id}` | 按 ID 删除 |
| `DELETE` | `/api/scores/student/{studentId}` | 删除某学生全部成绩 |
| `DELETE` | `/api/scores/course/{courseId}` | 删除某课程全部成绩 |

统计分析：

| 方法 | 端点 | 说明 |
|------|------|------|
| `GET` | `/api/scores/course/{id}/avg` | 课程平均分 |
| `GET` | `/api/scores/student/{id}/avg` | 学生平均分 |
| `GET` | `/api/scores/course/{id}/max` | 课程最高分 |
| `GET` | `/api/scores/course/{id}/min` | 课程最低分 |
| `GET` | `/api/scores/course/{id}/passRate` | 课程及格率 (≥60) |
| `GET` | `/api/scores/course/{id}/excellentRate` | 课程优秀率 (≥80) |
| `GET` | `/api/scores/course/{id}/distribution` | 成绩分布（5 个等级） |
| `GET` | `/api/scores/student/{id}/statistics` | 学生成绩汇总 |

### 用户管理

| 方法 | 端点 | 说明 |
|------|------|------|
| `GET` | `/api/users/username/{username}` | 按用户名查 |
| `GET` | `/api/users/{id}` | 按 ID 查 |
| `POST` | `/api/users` | 新增用户（密码自动加密） |
| `PUT` | `/api/users/password` | 修改密码 |

## 安全设计

- **密码**：BCrypt 哈希存储，登录时调用 `matches()` 比对
- **Token**：JWT (HS256)，有效期 24 小时，载荷 `{ sub, username, iat, exp }`
- **无状态**：`SessionCreationPolicy.STATELESS`，不创建 HTTP Session
- **过滤器**：`JwtAuthenticationFilter` 在每次请求时校验 Token 并注入 `SecurityContext`
- **白名单**：`/api/auth/**` 放行，其余路径均需认证

## 测试

```bash
mvn test
```

35 个集成测试，覆盖认证、CRUD、统计分析全部核心功能：

| 测试类 | 用例数 | 覆盖范围 |
|--------|--------|---------|
| `AuthControllerTest` | 4 | 登录、注册、Token 校验、未认证拦截 |
| `StudentControllerTest` | 7 | 增删改查、分页搜索、批量删除 |
| `CourseControllerTest` | 8 | 增删改查、多条件筛选、批量删除 |
| `ScoreControllerTest` | 11 | 三表 JOIN 查询、组合筛选、8 种统计分析 |
| `UserControllerTest` | 3 | 按用户名/ID 查询、密码修改 |
| `StudentsBackApplicationTests` | 1 | Spring 上下文加载验证 |
| `SecurityConfigTest` | 1 | SecurityFilterChain Bean 注册验证 |

测试基于 Spring Boot Test + MockMvc，使用 `@SpringBootTest` 全量加载上下文，通过自注册用户 + 登录获取 Token 的方式避免对种子数据的依赖。
