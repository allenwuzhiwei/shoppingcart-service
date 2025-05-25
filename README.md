
# 🛒 ShoppingCart MicroService - E-commerce Backend Module

## Overview

The **ShoppingCart Service** is a foundational microservice within our e-commerce platform.  
It is responsible for managing shopping cart lifecycle operations, such as creating shopping carts, adding items, updating quantities, and clearing cart contents.

It is built with **Spring Boot + Spring Data JPA**, and integrates with other services such as Order Service through REST APIs or Feign clients (to be implemented).


---

## Project Structure

The `shoppingcart-service` project follows a clean **Spring Boot + JPA** architecture,  
with clear separation of concerns across configuration, controller, service, repository, and entity layers.

- `config/`  
  ➤ API response model, global exception handler, Swagger config, and basic security configuration.

- `controller/`  
  ➤ Exposes RESTful endpoints for shopping cart operations (view, add, update, delete).

- `dto/`  
  ➤ Defines request payload objects for adding and updating cart items (e.g. `AddCartRequest`, `UpdateCartRequest`).

- `entity/`  
  ➤ Domain model classes that map to database tables: `ShoppingCart` and `CartItem`.

- `repository/`  
  ➤ Spring Data JPA repositories for `ShoppingCart` and `CartItem`.

- `service/` and `service/impl/`  
  ➤ Business logic for shopping cart management (lazy cart creation, quantity merging, total calculation).

- `resources/`  
  ➤ Configuration file (`application.properties`) and Spring Boot resource folder.

- `ShoppingCartApplication.java`  
  ➤ The main entry point of the Spring Boot application.

- `pom.xml`  
  ➤ Maven configuration with required dependencies.


---

## ✅ Implemented Features

### 🧺 Shopping Cart Module

#### ✅ Core Functions

- Create and initialize a new shopping cart automatically for first-time users
- Add a product to the cart (auto-merge if product exists)
- Get all cart items for a user
- Update product quantity in the cart
- Remove a specific product from the cart
- Clear all products from the cart

#### 💡 Extended Logic

- Automatically initializes a shopping cart if the user doesn't already have one.
- Uses `priceAtAdd` to store the price at the time of adding product (placeholder field, integration with Product Service optional in future).

---

## 👥 Collaborators

| Name         | Role               | Description                                                                                                                                               |
|--------------|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Song Yinrui** | Backend Developer  | Responsible for core backend development. Design and implement RESTful APIs, business logic, and API documentation/testing for ShoppingCart MicroService. |
| **Wu Zhiwei**   | System Architect    | Setting up the microservice architecture and framework.  Creating the corresponding database tables.                                                      |

---

## Author

- Song Yinrui
- National University of Singapore, Institute of Systems Science (NUS-ISS)
- Development Start: May 2025
