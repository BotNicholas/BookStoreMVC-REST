# Project Related info...
___

## `application.properties` file

---

`application.properties` file is not included so you have to specify it manually!!!
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://ENTER_HERE_HOST:3306/ENTER_HERE_DB_NAME
spring.datasource.username=ENTER_HERE_USER
spring.datasource.password=ENTER_HERE_PASSWORD
spring.jpa.hibernate.ddl-auto=update
#spring.jpa.show-sql=true
```
[How to connect MySQL to Spring boot here...](https://spring.io/guides/gs/accessing-data-mysql#_create_the_application_properties_file)

## Spring Boot version

---
In this project is used `Spring Boot v3.1.8`

## What is the difference between "simplicity" and "abstraction" mappers?

--- 

The main difference between `simplicity` and `abstraction`packages is that in the first one 
each entity has its own mapper, while in `abstratcion` package is defined only one implementation, that is generic
so it works for all the entities...
