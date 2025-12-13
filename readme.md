# 🚀 DevSync

DevSync is a **project & task management backend system** inspired by tools like Jira/Trello, built to demonstrate **real-world backend engineering concepts** such as authentication, authorization, RBAC, task workflows, and scalable architecture.

> ⚠️ This project is currently backend-first. Frontend and advanced integrations are planned.

---

## ✨ Features

### 🔐 Authentication & Security

* JWT-based authentication
* Secure login & role-based access
* Stateless session management

### 🧑‍💼 Role-Based Access Control (RBAC)

* **ADMIN** – full system control
* **PROJECT_MANAGER** – manages tasks & reviews
* **DEVELOPER** – basic task operations

### 📊 Task Workflow Management

Defined task lifecycle:

```
TODO → IN_PROGRESS → REVIEW → DONE
```

**Workflow Rules:**

* Only **Project Manager** can move a task to `REVIEW`
* Only **Admin** can move a task to `COMPLETED`

### 📝 Task Collaboration

* 💬 **Comments on tasks** for team discussion
* 📜 **Activity logs** to track every important action
* 🕒 **Task status history** to view complete lifecycle changes

### 🔔 Notifications

* 📧 **Email notifications** on task updates and status changes

### 📁 Project & Task Management

* Create projects
* Add **multiple users** to a project
* Assign tasks
* Update task status with workflow validation

---

## 🛠️ Tech Stack

| Layer            | Technology            |
| ---------------- | --------------------- |
| Language         | Java                  |
| Framework        | Spring Boot           |
| Security         | Spring Security + JWT |
| Database         | MySQL                 |
| ORM              | JPA                   |
| API Docs         | Swagger (OpenAPI)     |
| Build Tool       | Gradle                |
| Containerization | Docker (planned)      |

---

## 🧱 Architecture Overview

```
Controller  →  Service  →  Repository  →  Database
                ↓
         Security & RBAC Layer
```

* Controllers are thin
* Business rules live in Service layer
* Security handled via JWT + method-level authorization

---

## 🔑 Roles & Permissions

| Role            | Permissions                          |
| --------------- | ------------------------------------ |
| ADMIN           | Mark task as DONE, manage everything |
| PROJECT_MANAGER | Move task to REVIEW                  |
| DEVELOPER       | Other task updates                   |

---

## 📌 API Example

### Update Task Status

```
PUT /api/tasks/{taskId}/status?status=REVIEW
Authorization: Bearer <JWT_TOKEN>
```

---

## 🚧 Upcoming Features

* 🌐 Frontend integration
* 🐳 Dockerized deployment
* 📊 Advanced analytics & reports

---

## ▶️ How to Run Locally

```bash
# Clone repository
git clone https://github.com/your-username/devsync.git

# Navigate to project
cd devsync

# Run application (Gradle)
./gradlew bootRun
```

Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## 🤝 Contribution

This project is built for learning and showcasing backend skills.
Feel free to fork, explore, and suggest improvements.

---

## 📄 License

This project is licensed under the MIT License.

---

