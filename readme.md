# Customer Relationship Management System
This project contains web services for Customer Relationship Management System.

## Clone the repository
```shell
git clone https://github.com/Divyansh-Mehta/crm.git
```

## Running the application locally
Follow the steps to run the application on localhost:
1. Resolve the dependencies using
```shell
./mvnw dependency:resolve
```
2. In the resources folder create a secrets.properties file. You can check the example secret.properties given below to know what to add in this file.
3. Run the application with using
```shell
./mvnw spring-boot:run
```
#### Example secrets.properties
````
#Confidentail database config
database.name=<your-database-name>
database.username=<your-database-username>
database.password=<Your-database-password>

#Security config
#Note: - Use in basic auth username
spring.security.user.name=<auth-user-name>
#Note: - Use in basic auth password
spring.security.user.password=<auth-password>
````

### Api documentation
To view Swagger API docs run the server and browse to [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)