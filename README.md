<div style="text-align: center;">
  <h1>Gift Certificate System</h1>
</div>

1. [Project Overview](#project-overview)
2. [Built With](#built-with)
3. [Installation](#installation)
    - [Prerequisites](#prerequisites)
    - [Setup and Execution](#setup-and-execution)
    - [Testing](#testing)
4. [Module Requirements](#module-requirements)
5. [API Endpoints](#api-endpoints)

## Project Overview

<b>Gift Certificate System</b> is a comprehensive service designed for the sale of gift certificates. It is composed of
two components: a REST API and a user interface (UI). This repository exclusively hosts the <b>REST API</b>, providing
robust capabilities for certificate, tag, user, order, and role management. Additionally, it incorporates a robust
authentication and authorization system secured by Spring Security, leveraging the <b>JWT</b> as the authorization
mechanism.

This project was conceived and developed as a part of the <b>EPAM LAB</b> training program, comprising 8 distinct
modules, each assigned to a specific phase of development and individually evaluated. More detailed
here: [Module Requirements](#module-requirements)<br><br>
<b>UI part:</b> <a href="https://github.com/elpeanuto/gift-certificate-system-react"> react repository</a>


---

## Built With

* Spring Boot
* Spring Security
* Spring MVC
* Spring HATEOAS
* Spring Validation
* JSON Web Token
* Log4j2
* Hibernate Core
* Hibernate Envers
* H2 Database
* PostgreSQL
* JUnit
* Mockito
* Docker

---

## Installation

### Prerequisites

1. <b>Docker Daemon:</b> Make sure your Docker daemon is running. If it's not started, initiate it.

2. <b>Port Configuration:</b> If the default ports (5123 for the database and 8081 for the app) are already in use,
   modify the port settings accordingly. You can do this by editing the .env file. Update POSTGRES_PORT or APP_PORT as
   needed.

```
   POSTGRES_PORT=5123
   APP_PORT=8081
```

### Step 1: Setting Up the Project

To set up and run this project, follow these steps:

1. Open your preferred command-line interface.

2. Clone the project repository from the main branch using the following command:

   ```
     git clone -b main https://github.com/elpeanuto/gift-certificate-system.git
   ```

3. Enter the cloned repository directory:

   ```
     cd gift-certificate-system
   ```

4. Launch the Docker Compose environment by executing the following command:

   ```
     docker-compose up --build
   ```

### Step 2: Accessing the API

At this stage, you are now able to access the http://localhost:8081/giftCertificates URL, which serves as an open
endpoint without requiring any authorization. If everything is set up correctly, you should receive JSON data in
response.

If this initial access is successful, you have two options for further testing:

<b>Testing via Postman:</b>

1. **Access Postman on Your Computer:**
   If you don't have Postman installed, you can download it from the <a href="https://www.postman.com/downloads/">
   Postman website</a>.

2. **Download Collection:** Download JSON form <a href="https://drive.google.com/file/d/1jVT4RGunz2DWFuPEwf3j6fYrytAFz1co/view?usp=sharing">disk</a>.

3. **Importing a Collection:** Sign in. Click on the "Import" button located in the top-left corner of the Postman app. Drag downloaded file.

4. **Verification of Port Configuration:** Ensure that the port is correctly set. By default, the Spring application uses port 8081. If you need to modify API link, follow these steps:
   
   - Left-click on the collection.

   - Access the "Variables" section.

   - Modify the URL variable as necessary.

5. **Running tests:**
   Upon successfully importing the collection, you will find it listed among your collections on the left side of the Postman application. To execute the tests, perform the following:
   - Right-click on the collection.
   - Choose "Run collection." This will initiate all the available tests.

6. **Verifying Test Results:**
   Once the tests have been executed, Postman will present the results, including response data and any predefined test assertions.

<b>Testing via React App:</b>

   Alternatively, you can install the client UI code from the repository and conduct API
   testing via a React application. Detailed instructions for this process located in the README.md file of
   the <a href="https://github.com/elpeanuto/gift-certificate-system-react">repository</a>.<br> <b>NOTE:</b> This
   application is designed specifically for the certificate-related functionality of the API and does not provide a
   comprehensive UI implementation for the entire system.

[//]: # (Here are the admin credentials you can use for authentication:)

[//]: # ()

[//]: # (```)

[//]: # (Email: admin@gmail.com)

[//]: # (Password: admin)

[//]: # (```)

[//]: # ()

[//]: # (Using these credentials, you can access the necessary authentication token, allowing you to efficiently test the)

[//]: # (application's functionality and interact seamlessly with the API.)

---

## Module Requirements

Here 8 separate modules with practical tasks, each module is evaluated separately by a mentor, the evaluation includes
code review and online defense of the project and theory, as well as a demonstration of the work of the service:

1. Build Tool Advanced. Grade: `100/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%231.%20GIT%20%26%20Build%20Tools/build%20tools/build_tools_task.md">
   link </a>
2. Git Advanced. Grade: `100/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%231.%20GIT%20%26%20Build%20Tools/git/git_task.md">
   link </a>
3. Spring REST API Basics. Grade: `100/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%232.%20REST%20API%20Basics/rest_api_basics_task.md">
   link </a>
4. Spring REST API Advanced. Grade: `90/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%233.%20REST%20API%20Advanced/rest_api_advanced.md">
   link </a>
5. Authentication & Spring Security. Grade: `90/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%234.%20Authentication%20%26%20Spring%20Security/authentication_and_spring_security_task.md">
   link </a>
6. AWS Basics. Grade: `95/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%235.%20AWS/aws_task.md">
   link </a>
7. CI / CD Basics. Grade: `100/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%236.%20CI-CD/ci_cd_task.md">
   link </a>
8. Front-End Technologies. Grade: `100/100`
   Requirements: <a href="https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%237.%20UI/react/react_task.md">
   link </a> Repository: <a href="https://github.com/elpeanuto/gift-certificate-system-react"> react repository</a>

---

## API Endpoints

AuthController:

- POST http://localhost:8081/auth/authenticate
- GET http://localhost:8081/auth/isAdmin
- GET http://localhost:8081/auth/refreshToken
- POST http://localhost:8081/auth/register

OrderController:

- GET http://localhost:8081/orders
- POST http://localhost:8081/orders
- GET http://localhost:8081/orders/{{id}}
- DELETE http://localhost:8081/orders/{{id}}

GiftCertificateController:

- GET http://localhost:8081/giftCertificates
- POST http://localhost:8081/giftCertificates
- GET http://localhost:8081/giftCertificates/search
- GET http://localhost:8081/giftCertificates/{{id}}
- PATCH http://localhost:8081/giftCertificates/{{id}}
- DELETE http://localhost:8081/giftCertificates/{{id}}

TagController:

- GET http://localhost:8081/tags
- POST http://localhost:8081/tags
- GET http://localhost:8081/tags/widelyUsedTag
- GET http://localhost:8081/tags/{{id}}
- DELETE http://localhost:8081/tags/{{id}}

UserController:

- GET http://localhost:8081/users
- POST http://localhost:8081/users
- GET http://localhost:8081/users/{{id}}
- GET http://localhost:8081/users/{{userId}}/orders
- GET http://localhost:8081/users/{{userId}}/orders/{{orderId}}

RoleController:

- GET http://localhost:8081/roles
- POST http://localhost:8081/roles
- GET http://localhost:8081/roles/{{id}}
- DELETE http://localhost:8081/roles/{{id}}
