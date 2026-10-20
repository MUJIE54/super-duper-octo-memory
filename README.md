# springboot-web-01

Spring Boot Web 入门练习项目：从 `user.txt` 读取用户数据，通过 REST 接口返回 JSON，并配合前端页面展示用户列表。

## 技术栈

- **后端**：Spring Boot 4.1.1（spring-boot-starter-webmvc）
- **JDK**：17+
- **构建**：Maven
- **工具库**：
  - [Lombok](https://projectlombok.org/) — 实体类注解
  - [Hutool](https://hutool.cn/) 5.8.27 — 文件读取
- **前端**：原生 HTML + Vue 3（ESM 浏览器版）+ Axios

## 项目结构

```
springboot-web-01
├── pom.xml
└── src/main
    ├── java/com/itheima
    │   ├── SpringbootWeb01Application.java   # 启动类
    │   ├── controller/UserController.java    # 用户接口
    │   └── pojo/User.java                    # 用户实体类
    └── resources
        ├── application.properties             # 应用配置（JSON 日期格式/时区）
        ├── user.txt                           # 用户数据源（逗号分隔）
        └── static
            ├── user.html                      # 前端列表页
            └── js/
                ├── vue.esm-browser.js
                └── axios.min.js
```

## 环境要求

- JDK 17 或以上（本机 JDK 26 已验证可运行）
- Maven 3.6+
- IDEA / VS Code 任意

## 启动方式

### 方式一：IDEA 直接运行

打开 `SpringbootWeb01Application.java`，点击 main 方法旁边的绿色三角运行。

### 方式二：命令行

```bash
mvn spring-boot:run
```

启动后默认端口为 `8080`。

## 接口说明

### `GET /list`

读取 classpath 下的 `user.txt`，解析后返回用户列表（自动序列化为 JSON）。

**响应示例**：

```json
[
  {
    "id": 1,
    "username": "zhangsan",
    "password": "123456",
    "name": "张三",
    "age": 20,
    "updateTime": "2026-10-20 12:00:00"
  }
]
```

**`user.txt` 数据格式**（每行一条，逗号分隔）：

```
id,username,password,name,age,updateTime
```

日期格式：`yyyy-MM-dd HH:mm:ss`，时区 `GMT+8`。

## 前端页面

启动后浏览器访问：

```
http://localhost:8080/user.html
```

页面会通过 Axios 请求 `/list` 接口，把用户数据渲染成表格展示。

## 相关配置

- 应用名：`springboot-web-01`
- JSON 日期格式：`yyyy-MM-dd HH:mm:ss`
- 时区：`GMT+8`

## Git 仓库

https://github.com/MUJIE54/super-duper-octo-memory
