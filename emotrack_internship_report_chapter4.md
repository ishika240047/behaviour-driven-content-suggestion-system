# CHAPTER 4: DESIGN AND DEPLOYMENT OF A SECURE MICROSERVICES-BASED BEHAVIOR-DRIVEN CONTENT SUGGESTION SYSTEM (EMOTRACK) USING DOCKER

## 4.1 Project Overview
The practical component of the internship focused on applying the cloud computing concepts and technologies learned during the training to the design and deployment of a **Behavior-Driven Content Suggestion System (Emotrack)**. The project follows a microservices-based approach in which different application functionalities are organized into separate services. Docker is used to containerize the application components, while cloud computing concepts are applied to understand deployment, access management, and application management.

The main objective of the project was to understand how a web application can be structured into independent services, packaged using Docker containers, maintained using GitHub, and prepared for deployment in a cloud environment. The project also provided an opportunity to apply concepts related to Identity and Access Management (IAM) and secure access to cloud resources.

The system provides a web-based interface through which users can interact with a mood-based suggestion application. The frontend is responsible for user interaction (selecting current emotions and preferred media types), while backend services handle dynamic query processing, recommendations, and activity logs. By using a microservices architecture, the application components can be managed and deployed independently.

---

## 4.2 Objectives of the Project
The major objectives of the project were:
1. To understand the architecture of a microservices-based web application.
2. To understand the separation of frontend (static Web UI) and backend services (dynamic Java API server).
3. To containerize application components using Docker.
4. To practice commonly used Docker commands.
5. To understand the role of IAM in controlling access to cloud resources (e.g., database access, server environments).
6. To use GitHub for maintaining and managing project source code.
7. To understand the deployment of static and dynamic web applications.
8. To gain practical exposure to cloud-based application deployment (such as IBM Cloud and container services).
9. To understand basic security practices, database connection security, and local storage caching in a cloud-oriented web environment.

---

## 4.3 System Architecture
The Behavior-Driven Content Suggestion System follows a microservices-based architecture. Instead of implementing the complete application as one large unit, the system is divided into smaller and independently manageable components.

The major components of the system are:
1. **Frontend Service**: A modern, interactive web user interface built using HTML, CSS (glassmorphism design system), and JavaScript. It gathers the user's emotional state (Happy, Sad, Confused, Frustrated, Angry) and content type preference, displays recommendations, and logs interaction history.
2. **Backend Service**: A dynamic Java HTTP Server that handles requests at `/suggest`, `/history`, `/login`, and `/register` endpoints. It processes queries and fetches data.
3. **Database Layer**: A relational database structure containing tables for `USERS`, `EMOTIONS`, `CONTENT_TYPES`, `SUGGESTIONS`, and `USER_HISTORY`.
4. **Docker Containers**: The application components are packaged into Docker containers. The backend container hosts the Java HTTP Server, while the frontend is served via a web server container.
5. **Cloud Environment / IAM**: Identity and Access Management is used to control developer and application access to cloud services, databases, and container registries.
6. **GitHub Repository**: GitHub is used to maintain the project's source code and provide version-controlled storage for project files.

```
       +---------------------------------------------+
       |                  User Browser               |
       +----------------------+----------------------+
                              |
                              | HTTP Request
                              v
       +----------------------+----------------------+
       |               Frontend Service              |
       |  (Static Web Assets: HTML/CSS/JavaScript)   |
       +----------------------+----------------------+
                              |
                              | REST API Calls (Port 7070)
                              v
       +----------------------+----------------------+
       |               Backend Service               |
       |             (Java HTTP Server)              |
       +----------------------+----------------------+
                              |
                              | JDBC Connection
                              v
       +----------------------+----------------------+
       |               Database Layer                |
       |            (Oracle SQL Database)            |
       +---------------------------------------------+
```

---

## 4.4 Methodology
The project development and deployment process can be divided into the following stages:

*   **Step 1 – Understanding the Application Requirements**: Analyzing the relationship between user behavior (emotional states) and content suggestions (videos, articles, songs, quotes) to structure the data models.
*   **Step 2 – Designing the Application Structure**: Splitting the frontend (user input and result display) and the backend (API handling and database transactions) into decoupled microservices.
*   **Step 3 – Preparing the Application Components**: Writing the HTML/CSS/JS frontend files (including a smart local client-side mock system for demo reliability) and implementing the Java backend classes (`Server.java`, `DBConnection.java`, etc.).
*   **Step 4 – Containerization Using Docker**: Writing `Dockerfile` configurations for both frontend and backend services, and writing `docker-compose.yml` to launch the multi-container stack with a single command.
*   **Step 5 – Testing the Containers**: Verifying server port exposures (port `3000` for frontend, `7070` for backend) and testing communication between containerized components.
*   **Step 6 – Version Control Using GitHub**: Using Git branch management and commits to trace enhancements, clean directory structures, and push code to the repository.
*   **Step 7 – Access and Security**: Managing access controls for relational database drivers and establishing secure interfaces.
*   **Step 8 – Deployment**: Discussing how the frontend can be deployed statically on cloud storage (e.g., Object Storage/CDN) while the dynamic Java backend runs on container clusters (e.g., Kubernetes or Cloud Engine).

---

## 4.5 Docker Implementation
Docker is one of the major technologies used in the project. It provides a containerization platform that allows applications and their dependencies to be packaged together and executed in isolated environments.

The basic Docker workflow can be represented as:
$$\text{Application Source Files} \rightarrow \text{Dockerfile} \rightarrow \text{Docker Image} \rightarrow \text{Docker Container} \rightarrow \text{Dynamic Execution}$$

### Key Configurations:
*   **Frontend Dockerfile**: Built using a web server image (e.g., Nginx or lightweight HTTP server) to host the static HTML, CSS, and JS files.
*   **Backend Dockerfile**: Configured with a Java Runtime Environment (JRE) to compile and run the Java HTTP Server, exposing port `7070`.
*   **Docker Compose (`docker-compose.yml`)**: Coordinates the build parameters, container names, and port mappings.

```yaml
services:
  backend:
    build: ./backend
    ports:
      - "7070:7070"

  frontend:
    build: ./frontend
    ports:
      - "3000:80"
```

---

## 4.6 Security and Identity and Access Management (IAM)
Security is an important aspect of cloud-based applications. Identity and Access Management provides mechanisms for controlling who can access cloud resources and what operations they are allowed to perform.

In the project, IAM concepts were used to understand:
*   **Role-Based Access Control (RBAC)**: Controlling user permissions so that registered users can only write and read their own search logs in `USER_HISTORY`, while administrators can manage the global content `SUGGESTIONS` tables.
*   **Database Credentials Security**: Keeping connection credentials secure within the Java backend environment rather than exposing them directly to the client browser.
*   **Isolation**: Docker containers ensure runtime separation, meaning a vulnerability in the frontend web service cannot compromise the main database instance directly.

---

## 4.7 GitHub and Version Control
GitHub was used as part of the project workflow for managing source code and project files. Git provides version control, allowing changes to project files to be tracked over time.

During development, the repository structure was cleaned to separate frontend assets and backend classes into dedicated clean directories:
*   `/frontend/` containing `index.html`, `login.html`, `register.html`, `dashboard.html`, `history.html`, `css/style.css`, and `js/app.js`.
*   `/backend/` containing the consolidated Java source files (`Server.java`, `DBConnection.java`) and the backend Docker environment files.

Version control provided several advantages:
*   Maintained a clean commit history tracking architectural cleanups.
*   Prevented duplicate code configurations (removing redundant nested project folders).
*   Made source code ready for automated CI/CD pipeline deployments.

---

## 4.8 Deployment of the Application
The project provided practical understanding of both static and dynamic deployment.

### Static Website Deployment (Frontend)
The frontend consists of static assets (HTML5 pages, style sheets, and browser JS). These assets can be deployed directly into cloud-based static hosting services (such as AWS S3, IBM Cloud Object Storage, or Netlify/Vercel) coupled with a Content Delivery Network (CDN) to ensure fast load times worldwide.

### Dynamic Website Deployment (Backend)
The backend container runs the Java HTTP Server. This dynamic microservice must be deployed on cloud runtime compute models such as AWS ECS, IBM Cloud Code Engine, or a Kubernetes cluster, allowing it to scale dynamically based on request volumes.

---

## 4.9 Input and Output
The suggestion system accepts user interaction through the web interface.

*   **Input**: The user selects their emotional state (e.g., Happy, Sad, Frustrated) and selects the media type they feel like exploring (e.g., Video, Song, Article, Quote).
*   **Processing**: The frontend makes an asynchronous API request to the backend server. The backend Queries the database tables matching the emotion ID and content type ID, selects titles and links, and logs the access event.
*   **Output**: The application displays the list of suggestions as interactive glassmorphic cards. When a user clicks "Open & Explore", it records the duration of the activity and adds the interaction to their personal "History Log".

### Suggested Screenshot Placeholders for the Report:
1.  **Emotrack Homepage**: Showing the clean landing interface with glowing elements.
2.  **User Register & Login Pages**: Glassmorphism cards with input validation.
3.  **Interactive Selection Dashboard**: Selection cards for emotions and media types.
4.  **Curated Suggestion Cards**: Dynamic outputs loaded based on selected criteria.
5.  **Activity Log Table**: History data showing timestamps and duration spent.
6.  **Docker CLI Outputs**: Built images and running containers list.

---

## 4.10 Result and Discussion
The project provided practical exposure to the basic workflow of developing, containerizing, managing, and deploying a cloud-oriented web application.

Moving from a basic project structure to a clean, modern microservice layout demonstrated the advantages of modularity. Decoupling the frontend from the database-dependent backend allowed us to incorporate a high-fidelity local cache system in JavaScript (`localStorage`), making the frontend fully functional even during offline or backend service maintenance.

Overall, the project helped connect cloud computing internship learnings (containerization, deployment topology, and IAM roles) with a practical user-centric web application.

---

## 4.11 Learning Outcomes
The major learning outcomes from the project were:
1.  Understanding the microservices architecture of web applications.
2.  Developing container configurations for Java dynamic servers and static web pages.
3.  Utilizing Docker commands for multi-container management.
4.  Understanding secure database connection architectures and IAM policies.
5.  Organizing code repositories with version-control best practices.
6.  Distinguishing between static web distribution and dynamic container scaling.
7.  Implementing robust client-side caching fallbacks for resilient app design.
