# Online Store on JAVA Spring WEB
This project is a course project for java web courses at ITEA, a web application of Online Store that uses apple products as test data.
 ## Features
Project represent worked online store web application. Spring Web MVC framework is used, with JavaScript added. MySQL used as the database. Basic logic taken from [IteaShopT].
## The following pages implemented:
- Main page
- Product list
- User create/edit 
- User login
- Cart
- Checkout

## The following features implemented:
- Create user (date store in database)
- Edit user data
- Show product by categories
- Destroy user session
- Adding items to cart
- Shopping cart editing.
- Hashing user password
- Blocking brute-force password
- confirm order
## Pattern used
### MVC pattern
-	Created models for users, products and cart(order)
-	Created for all features using spring MVC
-	Created views for all implemented pages
### DAO pattern
-	Implemented loading data for user and products from different sources
(Only MySQL Implemented, basic XML support)
-	Implemented theme switching for front end (Implemented only  one)
## Tech
### On backend, Store built on JAVA infrastructure, specifically used:
* [Maven] -as software project management and comprehension tool
* [Spring] - End-to-end support for reactive & servlet based apps on the JVM
* [JEE Servlet API]
* [MySQL] - used as the database 
### On front side used 
* [Twitter Bootstrap] - great UI boilerplate for modern web apps
* [jQuery] – duh
## Getting Started
### Prerequisites
JAVA JDK at least version 8 
Maven
In order for the application to work properly, the database must be connected. The database description is in the database file.
### Build project
Build project directly by Maven
Open command prompt on main folder of project
Enter command
```
 mvn  package
```
 or open it in JAVA IDE, and run build in it
### Deploy project
After build of the project build, a WAR file created. This file must be hosted on a java web server (I'm using Apache Tomcat).
The database must be created. In file database describe structure of it. File  iteashop.sql is dump of testing date used by me 
### Run project
To access Store you must enter the url  “ path_to_you_ server/iteaShopT/ “in your browser ( like localhost:8080/iteaShopT/  for Tomcat)

[//]: # ()
[IteaShopT]: < https://github.com/gigo123/iteaShopT>
   [Maven]: < https://maven.apache.org/>
   [JEE Servlet API]: < https://javaee.github.io/servlet-spec/>
   [MySQL]: < https://www.mysql.com/>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: http://jquery.com
 [Spring]: <https://spring.io>

