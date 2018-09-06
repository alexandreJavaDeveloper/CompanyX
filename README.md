# RESTfull API for money transfers between accounts

Is assumed this API is invoked by another internal system/service.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Maven software for building and downloading dependencies to the project.
Not require a pre-installed container/server is necessary.

### Installing

To have and run the project on your local machine you can clone this project (command line):
```
git clone https://github.com/alexandreJavaDeveloper/CompanyX.git
```

## Running the tests

After downloading the project on your local machine, now is time to execute the tests.

For downloading all dependencies and to demonstrate the API with tests:
```
mvn clean package
```
NOTE: will have a lot of logs in the tests as expected.

Test Coverage more than 90%.

## License

This project is open source and can be used for future studies.

## To do

* Today the data store is running in-memory and is not using a real rollback method. In such case is necessary to create a rollback without parameters using JDBC, example: "Connection conn; conn.rollback()".
* Use currency attribute when using transfer with other countries. Was already created an Enum called Currency.
*  