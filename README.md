# Ebank-CQRS-EventSourcing-AXON
## Intro
The objective of this endeavor is to create a thorough application for managing bank accounts that complies with the standards of CQRS (Command Query Responsibility Segregation) and Event Sourcing patterns.

The utilization of both the Axon and Spring Boot frameworks will allow for the development of an application that empowers users to proficiently manage their bank accounts. This includes the creation of new accounts, depositing and withdrawing of funds, as well as monitoring the balance of their accounts with ease.

![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://a11ybadges.com/badge?logo=java)
![XAMPP](https://a11ybadges.com/badge?logo=xampp)
![IntelliJ IDEA](https://a11ybadges.com/badge?logo=intellijidea)

### CQRS

CQRS is one of the important pattern when querying between microservices. We can use CQRS design pattern in order to avoid complex queries to get rid of inefficient joins. CQRS stands for Command and Query Responsibility Segregation. Basically this pattern separates read and update operations for a database.

Normally, in monolithic applications, most of time we have 1 database and this database should respond both query and update operations. That means a database is both working for complex join queries, and also perform CRUD operations. But if the application goes more complex this query and crud operations will be also is going to be un-manageable situation.

![image](https://github.com/Dembelinho/Ebank-CQRS-ES-AXON/assets/110602716/544ed70b-eb51-48ec-97ff-d8ca44ba12a6)

### Event Sourcing Pattern in Microservices Architectures
The Event Sourcing pattern defines an approach to handling operations on data that's driven by a sequence of events, each of which is recorded in an append-only store. Application code sends a series of events that imperatively describe each action that has occurred on the data to the event store, where they're persisted. Each event represents a set of changes to the data

![image](https://github.com/Dembelinho/Ebank-CQRS-ES-AXON/assets/110602716/be80b633-63bb-4a57-8c35-8c635a5507cf)

When using CQRS with the Event Sourcing pattern, the main idea is store events into the write database, and this will be the source-of-truth events database. After that the read database of CQRS design pattern provides materialized views of the data with denormalized tables. Of course this materialized views read database consumes events from write database and convert them into denormalized views.

### AxonIQ
**Axon** Framework is the structure for the JVM that assists developers in implementing message-driven applications.
Used by developers and architects to:
 - Jumpstart development without reinventing the wheel
 - Build robust, distributed enterprise systems
 - Leverage open source, cost-free, and ready for immediate use
 - Accelerate time to market

## Project Idea & Architecture
![app github photo](https://github.com/Dembelinho/Ebank-CQRS-ES-AXON/assets/110602716/c03e1643-d90c-411f-a854-40df52028d28)
![app github photo2](https://github.com/Dembelinho/Ebank-CQRS-ES-AXON/assets/110602716/c277e19e-4ed6-41bf-b3b0-671c8448cada)

## Project Tree
```
├───.idea
├───.mvn
│   └───wrapper
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───sdia
│   │   │           └───ebankcqrseventsourcingaxon
│   │   │               ├───commands
│   │   │               │   ├───agreggates
│   │   │               │   └───controllers
│   │   │               ├───commonapi
│   │   │               │   ├───commands
│   │   │               │   ├───dtos
│   │   │               │   ├───enums
│   │   │               │   ├───events
│   │   │               │   ├───exceptions
│   │   │               │   └───queries
│   │   │               └───queries
│   │   │                   ├───controllers
│   │   │                   ├───entities
│   │   │                   ├───repositories
│   │   │                   └───services
    │           └───ebankcqrseventsourcingaxon
    │               ├───commands
    │               │   └───controllers
    │               └───commonapi
    │                   ├───commands
    │                   └───dtos
    └───generated-sources
        └───annotations
```
