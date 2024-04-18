# e_commerce_app
A microservices-based system that manages a simple e-commerce application. The system handles user authentication, product management, and order processing. Emphasis is placed on implementing concurrency control and ensuring the system can handle clustering for high availability.

### High-Level Architecture:

Our e-commerce application will be built using a microservices architecture, allowing for modularity, scalability, and ease of maintenance. The architecture consists of the following components:

1. **User Authentication Service:**
    - Responsible for user registration and authentication.
    - Utilizes Spring Boot for building RESTful APIs.
    - Stores user data in a PostgreSQL database.
    - Implements JWT-based authentication for secure access.

2. **Product Management Service:**
    - Manages product information, including CRUD operations.
    - Implements concurrency control using optimistic locking.
    - Built with Spring Boot and communicates with a PostgreSQL database for data storage.

3. **Order Processing Service:**
    - Handles order creation, management, and processing.
    - Exposes RESTful endpoints for order management operations.
    - Stores order data in a PostgreSQL database and updates product quantities accordingly.

4. **Database (MySql and Dynamodb):**
    - Stores user information, product data, and order history.
5. **API Gateway:**
    - Acts as a single entry point for clients to access microservices.
    - Routes requests to the appropriate microservice based on the endpoint.
    - Provides a centralized location for managing API access and security.

6. **Load Balancer:**
    - Distributes incoming traffic across multiple instances or containers of microservices.
    - Ensures scalability, fault tolerance, and high availability of the application.

### Workflow:

1. **User Registration and Authentication:**
    - Client sends registration requests to the User Authentication Service.
    - Service validates user credentials, stores user data in the database, and generates JWT tokens for authentication.
    - JWT tokens are sent back to the client for subsequent secure access to protected endpoints.

2. **Product Management:**
    - Client interacts with the Product Management Service to perform CRUD operations on products.
    - Service handles requests, implements concurrency control for product updates, and updates the database accordingly.
    - Optimistic locking ensures data integrity during concurrent updates.

3. **Order Processing:**
    - Client submits orders through the Order Processing Service.
    - Service creates orders, updates product quantities, and stores order information in the database.
    - Clients can retrieve their orders and track order status through the Order Processing Service.

### Resources Used:

- **Programming Language:** Java
- **Framework:** Spring Boot
- **Database:** MySQL, Dynamodb
- **Cloud Provider:** AWS (Amazon Web Services)
- **Deployment:** AWS ECS (Elastic Container Service)
- **Containerization:** Docker
- **Version Control:** Git
- **Testing Frameworks:** JUnit, Mockito
- **Communication Protocol:** RESTful APIs
- **Authentication Method:** JWT (JSON Web Tokens)
- **Concurrency Control:** Optimistic Locking
- **Monitoring:** AWS CloudWatch


### Technology Stack:
- Java with Spring Boot for microservices development.
- AWS for hosting and deployment.

### Concurrency Control:
- Implement Optimistic Locking for concurrency control in the Product Management Service.

### Clustering and High Availability:
- Use AWS ECS (Elastic Container Service) for clustering and high availability.
- Deploy microservices on multiple instances or containers within AWS.


### Authentication and Authorization:
- Implement JWT-based authentication for accessing protected endpoints.
- Use role-based authorization to restrict access to certain endpoints.

### Code Quality:
- Write clean, well-documented, and maintainable code following Java and Spring Boot best practices.
- Include meaningful comments and documentation.

### Version Control:
- Use Git for version control.
- Provide a Git repository with clear commit messages and a README file.

### Error Handling:
- Implement comprehensive error handling and logging using Spring Boot's logging framework.

### Testing:
- Write unit tests for critical components and concurrency control mechanisms using JUnit and Mockito.

### Deployment:
- Deploy microservices on AWS using ECS or EKS.
- Provide clear instructions for deploying and running microservices in the README file.


This architecture and workflow provide a robust foundation for building and deploying a scalable and reliable e-commerce application. Each component is carefully designed to fulfill specific functionalities while ensuring interoperability and efficiency across the system. Let me know if you need further details on any aspect of the architecture!



### Bonus Features (Optional):
Implement API rate limiting using Spring Boot's rate limiting features.
Use AWS SQS (Simple Queue Service) for asynchronous communication between microservices.
Implement caching strategies using Redis for improved performance.
Set up monitoring and alerting using AWS CloudWatch.



Lets dvelve into individual microservices.


1. ### User Registration and Authentication: ###
   Let's start by diving into the User Authentication Service. We'll cover the resources used, detailed working, code flow, high-level architecture, high-level architectural diagram, low-level design, endpoints, database design, different components, interactions with other microservices, and database schema.

### User Authentication Service:

#### Resources Used:
- **Programming Language:** Java
- **Framework:** Spring Boot
- **Database:** DynamoDB
- **Cloud Provider:** AWS (Amazon Web Services)

#### Detailed Working:
1. **User Registration:**
    - When a client sends a registration request, the User Authentication Service validates the request and stores user information in DynamoDB.
    - Passwords are hashed and securely stored in the database.
    - Upon successful registration, the service generates a JWT token and sends it back to the client for future authentication.

2. **User Authentication:**
    - For authentication, clients send login requests with credentials.
    - The User Authentication Service verifies the credentials against the stored user data in DynamoDB.
    - If the credentials are valid, the service generates a JWT token and sends it back to the client.

#### Code Flow:
1. **User Registration Flow:**
    - Client sends a registration request with user details.
    - User Authentication Service validates the request and stores user data in DynamoDB.
    - Service generates a JWT token and sends it back to the client.

2. **User Authentication Flow:**
    - Client sends a login request with credentials.
    - User Authentication Service verifies the credentials against DynamoDB.
    - If valid, the service generates a JWT token and sends it back to the client.



#### High-Level Architectural Diagram:
```
                                                                
                                 +-------------------------------------------+
                                 |                User Interface              |
                                 +-------------------------------------------+
                                           |            |            |
                                           v            v            v
                                 +-------------------------------------------+
                                 |                 API Gateway                 |
                                 +----------------+--------------+-----------+
                                                  |              |        
                                                  v              v
                                            +-----+--------------+------+
                                            |        Redis Cache         |
                                            +---------------------------+
                                                         |
                                                         v
                                                  CloudWatch Logs
                                                         |
                                                         v
                                                 Amazon ECS
                                                         |
                                                         v
                                 +-----------------------+-------------------+
                                 |     User Authentication Service           |
                                 |             +-------------+               |
                                 |             |  Database   |               |
                                 |             |  (DynamoDB) |               |
                                 +-------------+-------------+---------------+

           

#### Low-Level Design:
1. **Components:**
   - Controller: Handles incoming HTTP requests, validates data, and interacts with the service layer.
   - Service: Contains business logic, interacts with the database, and generates JWT tokens.
   - Repository: Manages database operations (CRUD) and interacts with DynamoDB.
   - Security: Implements JWT-based authentication and authorization.

2. **Endpoints:**
   - `/api/auth/register`: POST endpoint for user registration.
   - `/api/auth/login`: POST endpoint for user login.
   - `/api/auth/refresh`: POST endpoint for refreshing JWT token.
   - `/api/auth/logout`: POST endpoint for user logout.
   - `/api/auth/delete`: POST endpoint for user deletion.
   - `/api/auth/delete`: PUT endpoint for user deletion.

3. **Database Schema:**
   - Table: Users
     - Attributes: user_id (PK), username, password_hash, token

4. **Interactions with Other Microservices:**
   - User Authentication Service does not directly interact with other microservices. It provides authentication functionality for clients to access protected endpoints in other services.
