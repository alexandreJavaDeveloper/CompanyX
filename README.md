# RESTfull API for money transfers between accounts

Is assumed this API is invoked by another internal system/service.

## Dependencies and Technologies Used:

- Maven 			3
- JDK 				1.8
- JUnit 			4.12
- Tomcat embedded	7
- Jersey API 		1.9

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Maven software for building and downloading dependencies to the project.
Not require a pre-installed container/server is necessary.

### Installing

To run the project on your local machine you can clone this project (command line):
```
git clone https://github.com/alexandreJavaDeveloper/CompanyX.git
```

### Executing this API via a HTTP browser

Via commnand line, in the root program (where is the pom.xml file) execute:
```
mvn clean package
```
And now (to start the embedded Tomcat):
```
mvn tomcat:run
```

INFO: arguments sending by parameter:
{1} - receiverAccountNumber
{2} - senderAccountNumber
{3} - moneyToTransfer

In your browser:
```
http://localhost:8080/companyx/transfers/transfer/1A/2A/100.50
```

## Running the tests

After downloading the project on your local machine, now is time to execute the tests. Via command line, keep in the root program (where is the pom.xml file) for next executing.

For downloading all dependencies and building the project:
```
mvn clean package
```
NOTE: will have a lot of logs in the tests as expected.

Test Coverage > 90%.

## License

This project is open source and can be used for future studies.

## Top tips to do

* Today the data store is running in-memory and is not using a real rollback method. In such case is necessary to create a rollback without parameters using JDBC, example: "Connection conn; conn.rollback()".
* In case of fail in the rollback database? What to do? Give the responsibility to the database? Study the best option.
* Use currency attribute when using transfer with other countries. Was already created an Enum called Currency.
* To think about treatment with Threads, specially in the money transfer accessing the database for itself and other classes (today only the MoneyTransferService class access the database). The worries here is in case of new classes that could uses a database call (like retrieving an account) and can have inconsistent with the money transfers.