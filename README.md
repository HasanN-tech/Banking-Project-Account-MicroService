# Account Service – Banking Management Platform
## 📌 Project Overview
Account Service is a microservice developed as part of an **Enterprise Banking Management Platform** using Spring Boot and Microservices Architecture.
It is responsible for managing customer bank accounts, including account creation, account details retrieval, balance enquiry, account updates, account status management, and other account-related operations.
The service follows a layered architecture using Spring Boot, Spring Data JPA, Hibernate, MySQL, RESTful APIs, DTOs, validation, and centralized exception handling.

## 🚀 Features
- Create new bank accounts.
- Retrieve account details using Account ID.
- Retrieve all bank accounts.
- Retrieve accounts based on Customer ID.
- Update existing account information.
- Check account balance.
- Support different account types such as Savings and Current.
- Manage account status (ACTIVE / INACTIVE).
- Pagination and sorting support.
- Search and filter account records.
- Request validation using Jakarta Validation.
- Centralized exception handling.
- Standardized API responses.
- Automatic creation and modification timestamps.
- RESTful API development.

## 🏗️ Project Architecture
The application follows a layered architecture:
Account-Service
       |
       v
    Controller
       |
       v
     Service
       |
       v
   ServiceImpl
       |
       v
   Repository
       |
       v
      MySQL

### Package Structure
com.hasan
    |
    |-- controller
    |-- DTO
    |-- entity
    |-- repository
    |-- service
    |-- service.impl
    |-- exception
    |-- config
    |-- AccountServiceApplication.java

## 🗄️ Database Configuration
The application uses MySQL as its primary relational database.
Configure the following properties in `application.properties`:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/account_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

**Note:** Replace the database username and password with your local MySQL credentials.

## ⚙️ Prerequisites
Before running the application, ensure the following are installed:
- JDK 17 or above
- Maven
- MySQL Server
- IntelliJ IDEA / Eclipse
- Postman (for API testing)

## ▶️ How to Run the Project
### Step 1: Clone the Repository
git clone YOUR_GITHUB_REPOSITORY_URL

### Step 2: Navigate to the Project Directory
cd Account-Service

### Step 3: Create the MySQL Database
CREATE DATABASE account_db;

### Step 4: Configure Database Credentials
Update the database URL, username, and password in `application.properties`.

### Step 5: Build the Application
mvn clean install

### Step 6: Run the Application
mvn spring-boot:run
The Account Service will start on the configured application port.

## 🔗 API Functionalities
| HTTP Method | Functionality |
|---|---|
| POST | Create Bank Account |
| GET | Get Account By ID |
| GET | Get All Accounts |
| GET | Get Accounts By Customer ID |
| GET | Check Account Balance |
| PUT | Update Account Details |
| DELETE | Delete Account / Change Account Status |
| GET | Search Accounts |
| GET | Pagination and Sorting |

*Note: Update endpoint paths according to your actual controller mappings.*

## 🧪 API Testing
All REST APIs can be tested using Postman.
Test the following scenarios:
- Creating a new bank account.
- Retrieving account details using Account ID.
- Retrieving all customer accounts.
- Fetching accounts using Customer ID.
- Checking account balance.
- Updating account information.
- Searching accounts using different filters.
- Testing pagination and sorting.
- Validating invalid requests.
- Handling account not found exceptions.
- Activating and deactivating accounts.

## ⚠️ Exception Handling
The application implements centralized exception handling to manage application errors consistently.
Examples:
- AccountNotFoundException
- ResourceNotFoundException
- BusinessException
- MethodArgumentNotValidException
- GlobalExceptionHandler

## 🎯 Learning Outcomes
Through this project, I gained practical experience in:
- Developing banking microservices using Spring Boot.
- Implementing layered architecture.
- Integrating MySQL using Spring Data JPA and Hibernate.
- Implementing RESTful APIs.
- Working with DTOs and entity relationships.
- Implementing validation and exception handling.
- Developing pagination, sorting, and search functionality.
- Managing bank account information and account lifecycle.
- Managing source code using Git and GitHub.

## 👨‍💻 Developer
**Mohd Hasan**
Java Backend Developer | Spring Boot | Microservices | MySQL
GitHub: [Your GitHub Profile](https://github.com/)

⭐ If you find this project useful, feel free to star the repository!
