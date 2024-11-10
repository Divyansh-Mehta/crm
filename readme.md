# Customer Relationship Management System
The main objective of the project is to develop RESTful web services for a CRM (Customer Relationship Management) system aimed at empowering businesses to efficiently manage and analyze customer data, track sales activities, and log interactions. This CRM system will provide sales and customer service teams with actionable insights into customer preferences, trends, and engagement patterns, ultimately improving customer relationships and enhancing business growth.


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
2. Create a database in mysql. 
- Open mysql client and use following query:
```sql
CREATE DATABASE <your-database-name>;
```
3. In the resources folder at `src/main/resources` create a `secrets.properties` file. 
- You can check the example `secret.properties` given below to know what to add in this file.
4. Run the application using
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

### API documentation
To view Swagger API docs run the server and browse to [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Features
1. **Customer Profile Management: -** Store and manage detailed customer profiles to centralize data for easy access.
2. **Sales Tracking: -** Monitor the sales progress through various stages such as lead generation, contact, demo, and closure to prioritize high-value opportunities and forecast revenue.
3. **Customer Interaction Logs:-** Maintain a log of each customer interaction, including date, type, notes, and associated sales activity, to ensure continuity in communication and strengthen customer relationships.
4. **Reporting and Analytics:-** Generate reports on customer interaction, sales performance, and customer growth and retention to drive strategic, data-driven decision-making.

## Tech stack
- **Programming Language: -** Java
- **Frameworks: -** Spring boot, Spring Web MVC, Spring Data JPA, Spring Security
- **Database: -** MySql
- **Built Tool: -** Maven
- **IDE: -** IntelliJ IDEA
- **Source code control: -** Git/Github
- **Monitoring and Documentation: -** SwaggerUi, Spring boot actuator
- **Logging: -** SLF4J/Logback
- **Testing: -** Junit, Mockito, MockMVC
- **Manual testing: -** Postman
