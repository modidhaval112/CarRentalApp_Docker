# SOEN6461-SoftwareDesignMethodologies
 Software Design Methodologies Course Project

## Team Composition

| Name  | Student id | e-mail | phone number | GitHub username |
| --- | --- | --- | --- | ---|
| Himalaya Yadav Reddiboina | 40070312|himalayayadav444@gmail.com |(438) 725-3384|Himalayayadav444 |
|Dheeraj Ashok  Shobha|40082192|dheeraj.as008@gmail.com |(438) 728-1488|RealNameHidden|
|Martin Morin| 26410383 |martin.morin.002@gmail.com |(438) 401-8225|martin-morin|
|Manasa Murali|40082609|m.manasamurali@gmail.com |(514) 692-2595|manasamurali|
|Dhaval Chandreshkumar Modi|40083289|dhavalmodi556@gmail.com |(514) 638-0925|Modidhaval112|
|Sai Krishna Chadalavada|40087977|saich94@gmail.com |(514) 553-5064|saich9424


## Team leads per iteration

| Iteration | lead |
| --- | --- |
|1 | Martin Morin |
|2 | Martin Morin |
|3 | Martin Morin |
|4 | Martin Morin |

## How to work on this project

The project is no longer build and tested using the GitHub action since the tests relies on data coming from a database.

**In the same directory as the SAD and SRS screen-cast videos are available to present how to setup to run the application and some of the functionalities delivered this iteration.**

**The instructions to check concurrency functionalities is available in the online help when the application is executed**

### Code Style Convention
Since this repository is using JAVA, the following guide line must be followed:

* Code Conventions for the Java Programming Language (http://www.oracle.com/technetwork/java/javase/documentation/codeconventions-135099.html).

### Database setup
- The application needs to connect to an instance of MySQL
- The database connection information can be found in the file:".\src\main\resources\application.properties"
- The configuration to preset the database with tables and some hard coded values can be found in the file: ".\schema-mysql.sql"

### How to build the project using IntelliJ

1. Run the Maven life cycle install
2. Run the car rental application
3. Open your browser to http://localhost:8080/login

### How to use the project
1. Open your browser to http://localhost:8080/login
2. For clerk access, enter the following credentials 
    - First Clerk : username(clerk) password(clerk)
    - Second Clerk : username(clerk1) password(clerk1)
3. For Administrator access, enter the following credentials 
    - First Admin - username(admin) and password(admin)
    - Second Admin - username(admin1) and password(admin1)
4. If you want to add more users, please refer from ".\schema-mysql.sql", and look for 'INSERT INTO USERS' query.

Use one of the following login

| role | username | password |
| --- | --- | --- |
| clerk | clerk | clerk |
| clerk | clerk1 | clerk1 |
| administrator| admin | admin |
| administrator| admin1 | admin1 |

### Concurrency

For Concurrency on 'Client Record' and 'Transactions' please refer to 'Online Help' section after logging in as 'Clerk'.
