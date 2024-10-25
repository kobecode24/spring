
# User Management System

This application is a basic User Management System built using Spring Core without Spring Boot. It demonstrates the usage of Spring MVC and Spring Data JPA to perform CRUD operations on user data through a web interface.

## Table of Contents
- [Project Structure](#project-structure)
- [Dependency Injection (DI)](#dependency-injection-di)
- [Inversion of Control (IoC)](#inversion-of-control-ioc)
- [Spring Beans](#spring-beans)
- [Bean Scopes](#bean-scopes)
- [ApplicationContext](#applicationcontext)
- [Component Scanning and Stereotype Annotations](#component-scanning-and-stereotype-annotations)
- [Spring Data JPA](#spring-data-jpa)
- [Spring MVC](#spring-mvc)
- [Installation and Setup](#installation-and-setup)
- [Features](#features)

## Project Structure

```
user-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── technova/
│   │   │           └── usermanagement/
│   │   │               ├── controller/
│   │   │               │   └── UserController.java
│   │   │               │   └── HomeController.java  
│   │   │               ├── model/
│   │   │               │   └── User.java
│   │   │               ├── repository/
│   │   │               │   └── UserRepository.java
│   │   │               └── service/
│   │   │                   ├── UserService.java
│   │   │                   └── UserServiceImpl.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           ├── views/
│   │           │   ├── userList.jsp
│   │           │   ├── userForm.jsp
│   │           │   └── editUser.jsp
│   │           ├── applicationContext.xml
│   │           └── web.xml
│   └── test/
└── pom.xml
```

## Dependency Injection (DI)

The project demonstrates dependency injection through XML configuration. Key components:

- **UserController** depends on **UserService**
- **UserServiceImpl** depends on **UserRepository**
- Dependencies are injected using both constructor injection and setter injection.

#### Example from `applicationContext.xml`:

**Setter Injection**:
```xml
<bean id="userController" class="com.technova.usermanagement.controller.UserController">
    <property name="userService" ref="userService"/>
</bean>
```

**Corresponding Java Code**:
```java
public class UserController implements Controller {

    private UserService userService;

    // Setter method for Dependency Injection
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
```

**Constructor Injection**:
```xml
<bean id="userController" class="com.technova.usermanagement.controller.UserController">
    <constructor-arg ref="userService"/>
</bean>
```

**Corresponding Java Code**:
```java
public class UserController implements Controller {

    private final UserService userService;

    // Constructor for Dependency Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

#### Differences between Setter and Constructor Injection:

1. **Setter Injection**:
    - Injects dependencies by calling setter methods on the bean after it is created.
    - Allows optional dependencies, as not all setters must be called.
    - Provides flexibility when the dependencies can change over the bean's lifecycle.

2. **Constructor Injection**:
    - Injects dependencies through the constructor when the bean is created.
    - Ensures that the dependencies are provided when the bean is instantiated, making them mandatory.
    - Useful for establishing a bean that must have all required dependencies set before it can be used.

## Inversion of Control (IoC)

The application uses Spring's IoC container to manage beans and their lifecycles. Configuration is done through XML rather than annotations to demonstrate traditional Spring setup.

## Spring Beans

Main beans in the application:

- **UserController**: Handles web requests
- **UserService**: Business logic layer
- **UserRepository**: Data access layer

Bean definitions are in `applicationContext.xml` and include:

- Dependency injection configuration
- Bean scope definitions
- Property configurations

## Bean Scopes

The application uses different bean scopes:

- **Singleton**: For service and repository beans
- **Prototype**: For model objects

Example:

```xml
<bean id="userService" class="com.technova.usermanagement.service.UserServiceImpl" scope="singleton">
    <property name="userRepository" ref="userRepository"/>
</bean>
```

## ApplicationContext

The application uses XML-based configuration with `applicationContext.xml`. Key configurations include:

- Component scanning
- Database configuration
- View resolver setup
- Transaction management

## Component Scanning and Stereotype Annotations

While the project primarily uses XML configuration, it demonstrates the use of JPA repositories through component scanning.

## Spring Data JPA

Database interaction is handled through Spring Data JPA:

- Entity mapping with JPA annotations
- Repository interface extending `JpaRepository`
- Transaction management configuration

Entity example:

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String identificationDocument;
    private String nationality;
    private LocalDate registrationDate;
    private LocalDate expirationDate;
}
```

## Spring MVC

The application follows the MVC pattern:

- **Controllers** handle HTTP requests
- **JSP views** render the UI
- **Model objects** carry data between views and controllers

URL mappings:

- `GET /users` - List all users
- `GET /users/new` - Show new user form
- `POST /users/save` - Save new user
- `GET /users/{id}/edit` - Show edit form
- `POST /users/save` - Update user
- `GET /users/{id}/delete` - Delete user

## Installation and Setup

### Prerequisites

- JDK 11 or later
- Maven 3.6 or later
- PostgreSQL 12 or later

### Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE user_management;
```

Update database configuration in `applicationContext.xml`:

```xml
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="org.postgresql.Driver"/>
    <property name="url" value="jdbc:postgresql://localhost:5432/user_management"/>
    <property name="username" value="your_username"/>
    <property name="password" value="your_password"/>
</bean>
```

### Building the Application

Clone the repository:

```bash
git clone https://github.com/kobecode24/spring.git
```

Navigate to project directory:

```bash
cd spring
```

Build the project:

```bash
mvn clean install
```

### Deployment

- Deploy the WAR file to your application server (e.g., Tomcat, WildFly)
- Access the application at: `http://localhost:8080/`

## Features

- Create new users with validation
- View list of all users
- Edit existing users
- Delete users

Validation for:

- Unique identification documents
- Valid expiration dates
- Required fields
