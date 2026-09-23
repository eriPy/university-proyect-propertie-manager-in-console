Real Estate System

Console-based real estate management system.

Description

A console application that allows users to manage properties (houses, apartments, land, buildings) and handle market operations involving them: leasing, selling, renovation, repair, and transfer. The system has two roles: Admin (manages properties) and User (performs operations on them).

Technologies

Java 17

Maven — dependency and build management

JPA (Jakarta Persistence 3.1) + Hibernate 6.6 — persistence

MySQL 8 — database

Lombok — boilerplate reduction in some classes

Architecture

The project follows a layered architecture inspired by Spring Boot, but implemented manually (without a dependency injection container — object wiring is done manually through constructors):

com.grupo6.realestate
├── entity/              JPA entities (@Entity): Admin, Propertie, Transaction, User
│   └── enums/            Domain enums: Department, ListingStatus, MarketTransaction,
│                          PropertyCondition, RealStateCategory, UserType
├── dao/                 Data access through EntityManager: AdminDao, PropertieDao,
│                          TransactionDao, UserDao
├── service/             Business logic: AdminService, PropertieService,
│                          TransactionService, UserService
│   └── operations/       Market operations implemented as interfaces with default methods:
│                          Evaluate, LeaseOperation, RenovationOperation,
│                          RepairOperatoon, SellOperation, TransferOperation
├── questionaries/       Console interaction flows: AdminFunctions,
│                          TransactionFuncions
├── exceptions/          Custom exceptions: InvalidDataRequest, ServiceException,
│                          TransactionException
├── util/                JpaUtil — EntityManager factory
└── Main.java            Entry point, wires dependencies, and runs the menu

Layers

entity: Classes annotated with @Entity, mapped 1-to-1 to the tables in real_state_db.

dao: Each method opens its own EntityManager (open–operation–close pattern), handles explicit transactions for write operations, and uses JPQL for queries, including dynamic property searches.

service: Contains the business logic and handles user interaction through the console (Scanner). Market operations (lease, sell, renovation, repair, transfer) are implemented as interfaces with default methods since they share a common structure but do not share state. They are combined through interface composition rather than inheritance.

questionaries: Groups menu flows by user type (admin functions and transaction functions).

util: JpaUtil exposes the static EntityManagerFactory, built from the realStatePU persistence unit.

Database Configuration

Create the schema in MySQL:

CREATE SCHEMA real_state_db;


Create an application user (avoid using root):

CREATE USER 'usuario'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'contraseña';
GRANT ALL PRIVILEGES ON *.* TO 'usuario'@'localhost';
FLUSH PRIVILEGES;


Configure src/main/resources/META-INF/persistence.xml with the credentials of the user you created. hibernate.hbm2ddl.auto=update automatically creates and updates the tables when the application runs — there is no need to create them manually.

How to Run
mvn clean compile exec:java