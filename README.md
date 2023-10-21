# **online-book-store**

Book store is a service which allows register customers and buy books. This API supports authentication, registration, CRUD (Create, Read, Update,Delete) operations with entity of database.

## **Features:**

registration user with role "USER";
authentication user;
create/get list of books;
create/get categories of books;
add books to a shopping cart;
edit a shopping cart;
checkout an order for an authenticated user;
complete an order and get list of orders for an authenticated user;
under role ADMIN can edit statuses of an order

## **Technologies Used:**

Spring boot framework 3.1.4
JSON web token 0.11.5
Java 17
Maven 3.1.1
Mapstruct 0.2.0
Test containers 1.18.3
Lombok 1.18.30
MySQL 8.0.33
Docker 3.1.4
Liquibase 4.20.0

# _**Endpoints available for used api**_

## **Available for non authenticated users:**

POST: /api/auth/register - register a new user,
POST: /api/auth/login - authenticate in system.

## **Available for users with role USER:**

GET: /api/books - get list of all books,
GET: /api/books/{id} - get a book by a book id,
GET: /api/categories - get list of all categories,
GET: /api/categories/{id} - get a category by a category id,
GET: /api/categories/{id}/books - get list of books by category id,
GET: /api/cart - get books from shopping cart,
POST: /api/cart - add a book to shopping cart,
PUT: /api/cart/cart-items/{cartItemId} - update book quantity in shopping cart,
DELETE: /api/cart/cart-items/{cartItemId} - delete a book from shopping cart,
GET: /api/orders - get list of all orders by user
POST: /api/orders - create a new order,
GET: /api/orders/{orderId}/items - get list of all order items by order id,
GET: /api/orders/{orderId}/items/{itemId} - get a order item by order item id.

## **Available for users with role ADMIN:**

POST: /api/books/ - create a new book,
PUT: /api/books/{id} - update data about book,
DELETE: /api/books/{id} - delete a book,
POST: /api/categories - create a new category,
PUT: /api/categories/{id} - update info about category,
DELETE: /api/categories/{id} - delete a category,
PATCH: /api/orders/{id} - update a status of user`s 

# **Note on use the api**

Please note that when using the api endpoints with methods POST, PUT, PATCH required JSON body. Security builds on technology jackson web token (JWT) with using Bearer token.

# **Instructions:**

To run the book store API on a server, you will need to first run Docker.
After running Docker in first time will create all necessary tables in database
To register an Administrator in the system, you will need to use the endpoint "POST: /api/auth/register" just like a usual user. Once the Administrator user is registered, you can add a record in the table user_roles with the following query: "INSERT INTO user_roles (user_id, role_id) values(ID_Administrator, 2);" where "ID_Administrator" is the ID of the Administrator from the table "users". This will assign the "ADMIN" role to the newly registered user.
All credentials for connect to database you can set in file ".env"