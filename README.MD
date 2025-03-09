<h1 align="center" style="font-weight: bold;">Networking Platform 💻</h1>

<p align="center">
 <a href="#technologies">Technologies</a> • 
 <a href="#features">Features</a> • 
 <a href="#getting-started">Getting Started</a> •
 <a href="#api-endpoints">API Endpoints</a>
</p>

<p align="center">
    <b>A networking platform that allows users to manage profiles, skills, education, job postings, and more.</b>
</p>

---

<h2 id="technologies">💻 Technologies</h2>

- Java (Spring Boot)
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Maven

---

<h2 id="features">✨ Features</h2>

- **🧑‍💼 User Management**
    - Register
    - Authenticate
    - Update
    - Manage users

- **👤 User Profiles**
    - Maintain detailed profiles
    - Add skills, education, and work experience

- **🏢 Jobs & Companies**
    - Post job openings
    - Search available jobs
    - Create, update, or remove companies

- **💡 Endorsements**
    - Endorse other users’ skills
    - Showcase professional strengths

- **💬 Connections & Chats**
    - Send connection requests
    - Engage in private chats

- **📝 Posts & Comments**
    - Create posts in a social feed
    - Comment on and like posts

- **👑 Advanced Administration**
    - Manage roles
    - Moderate posts and comments
    - Review system statistics

---

<h2 id="getting-started">🚀 Getting Started</h2>

Below are some steps to help you set up and run the project locally.

<h3>Prerequisites</h3>

- <a href="https://git-scm.com/">Git</a>
- <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/">Spring Boot</a>
- <a href="https://www.postgresql.org/">PostgreSQL</a> (or an alternative database)

<h3>Cloning</h3>

```bash
git clone https://github.com/galileo680/networking-platform.git
```
<h3>Config .env or application.properties</h3>
Use an appropriate configuration file to set environment variables or credentials:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/networking_platform
spring.datasource.username=DB_USER
spring.datasource.password=DB_PASSWORD

jwt.secret=JWT_SECRET
```

<h3>Running</h3>

1. Navigate to the project directory:
```bash
cd networking-platform
```
2. Build the project
```bash
mvn clean install
```
3. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

<h2 id="api-endpoints">📍 API Endpoints</h2>
Below is a high-level overview of the main API routes:

1. <strong>Users & Authentication</strong>

<kbd>POST /api/auth/register</kbd> – register a new user

<kbd>POST /api/auth/login</kbd> – user login

<kbd>POST /api/auth/logout</kbd> – user logout

<kbd>POST /api/auth/forgot-password</kbd> – password recovery

<kbd>GET /api/users/{id}</kbd> – get user data

<kbd>PUT /api/users/{id}</kbd> – update user data

<kbd>GET /api/admin/users</kbd> – get all users 

<kbd>PUT /api/admin/users/{id}/status</kbd> – block/unblock a user

2. <strong>User Profile</strong>

<kbd>GET /api/profiles/{id}</kbd> – get profile data

<kbd>PUT /api/profiles/{id}</kbd> – update profile data

<kbd>GET /api/profiles/{id}/skills</kbd> – get profile skills

<kbd>POST /api/profiles/{id}/skills</kbd> – add skill to profile

<kbd>DELETE /api/profiles/{id}/skills</kbd> – remove skill from profile

<kbd>GET /api/profiles/search?query=...</kbd> – search profiles

3. <strong>Education</strong>

<kbd>GET /api/profiles/{id}/educations</kbd> – get education records

<kbd>POST /api/profiles/{id}/educations</kbd> – add education record

<kbd>PUT /api/profiles/{id}/educations/{educationId}</kbd> – update education

<kbd>DELETE /api/profiles/{id}/educations/{educationId}</kbd> – delete education

4. <strong>Work Experience</strong>

<kbd>GET /api/profiles/{id}/experiences</kbd> – get experiences

<kbd>POST /api/profiles/{id}/experiences</kbd> – add experience

<kbd>PUT /api/profiles/{id}/experiences/{experienceId}</kbd> – update experience

<kbd>DELETE /api/profiles/{id}/experiences/{experienceId}</kbd> – delete experience

5. <strong>Skills</strong>

<kbd>GET /api/skills</kbd> – get all skills

<kbd>GET /api/skills/{id}</kbd> – get skill by ID

<kbd>POST /api/skills</kbd> – create skill (admin)

<kbd>PUT /api/skills/{id}</kbd> – update skill (admin)

<kbd>DELETE /api/skills/{id}</kbd> – delete skill (admin)

<kbd>GET /api/skills/search?query=...</kbd> – search skills
6. Endorsements</strong>

<kbd>GET /api/profiles/{profileId}/skills/{skillId}/endorsements</kbd> – get endorsements for a skill

<kbd>POST /api/profiles/{profileId}/skills/{skillId}/endorsements</kbd> – add endorsement

<kbd>DELETE /api/endorsements/{id}</kbd> – remove endorsement
7. <strong>Companies</strong>

<kbd>GET /api/companies</kbd> – get all companies

<kbd>GET /api/companies/{id}</kbd> – get company by ID

<kbd>POST /api/companies</kbd> – create company

<kbd>PUT /api/companies/{id}</kbd> – update company

<kbd>DELETE /api/companies/{id}</kbd> – delete company

<kbd>GET /api/companies/search?query=...</kbd> – search companies

8. <strong>Job Postings</strong>

<kbd>GET /api/jobs</kbd> – get all job postings

<kbd>GET /api/jobs/{id}</kbd> – get job posting by ID

<kbd>POST /api/jobs</kbd> – create job posting

<kbd>PUT /api/jobs/{id}</kbd> – update job posting

<kbd>DELETE /api/jobs/{id}</kbd> – delete job posting

<kbd>GET /api/jobs/search?query=...&location=...</kbd> – search job postings

<kbd>GET /api/companies/{companyId}/jobs</kbd> – get job postings by company

9. <strong>Job Applications</strong>

<kbd>GET /api/users/{userId}/applications</kbd> – get user applications

<kbd>GET /api/jobs/{jobId}/applications</kbd> – get applications for a job

<kbd>POST /api/jobs/{jobId}/applications</kbd> – apply for a job

<kbd>PUT /api/applications/{id}/status</kbd> – update application status

10. <strong>User Connections</strong>

<kbd>GET /api/users/{userId}/connections</kbd> – get user connections

<kbd>GET /api/users/{userId}/connections/pending</kbd> – get pending invites

<kbd>POST /api/users/{userId}/connections</kbd> – send connection invite

<kbd>PUT /api/connections/{id}/accept</kbd> – accept invitation

<kbd>PUT /api/connections/{id}/reject</kbd> – reject invitation

<kbd>DELETE /api/connections/{id}</kbd> – remove connection
11. <strong>Chats & Messages</strong>

<kbd>GET /api/users/{userId}/chats</kbd> – get user chats

<kbd>POST /api/chats</kbd> – create new chat

<kbd>POST /api/chats/{chatId}/participants</kbd> – add participant

<kbd>DELETE /api/chats/{chatId}/participants/{userId}</kbd> – remove participant

<kbd>GET /api/chats/{chatId}/messages</kbd> – get messages in a chat

<kbd>POST /api/chats/{chatId}/messages</kbd> – send message

<kbd>DELETE /api/messages/{id}</kbd> – remove message

12. <strong>Posts, Comments & Likes</strong>

<kbd>GET /api/feed</kbd> – get feed of posts

<kbd>GET /api/users/{userId}/posts</kbd> – get user posts

<kbd>POST /api/posts</kbd> – create post

<kbd>PUT /api/posts/{id}</kbd> – update post

<kbd>DELETE /api/posts/{id}</kbd> – delete post

<kbd>GET /api/posts/{postId}/comments</kbd> – get comments for a post

<kbd>POST /api/posts/{postId}/comments</kbd> – add comment

<kbd>PUT /api/comments/{id}</kbd> – update comment

<kbd>DELETE /api/comments/{id}</kbd> – delete comment

<kbd>POST /api/posts/{postId}/likes</kbd> – like a post

<kbd>DELETE /api/posts/{postId}/likes</kbd> – unlike a post

<kbd>GET /api/posts/{postId}/likes</kbd> – get who liked the post

13. <strong>Administration</strong>

<kbd>GET /api/admin/statistics</kbd> – get system statistics

<kbd>GET /api/admin/roles</kbd> – manage roles

<kbd>PUT /api/admin/users/{userId}/roles</kbd> – assign user roles

<kbd>PUT /api/admin/posts/{postId}/moderate</kbd> – moderate posts

<kbd>PUT /api/admin/comments/{commentId}/moderate</kbd> – moderate comment
