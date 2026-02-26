# Spring Boot GraphQL

A Spring Boot and GraphQL application inspired by Dan Vega's tutorial on simplifying GraphQL by using Query By Example.

[Dan's code](https://github.com/danvega/graphql-qbe/tree/main)

The exciting part is there is no need for a Controller, Service or Data Mappers.
GraphQL uses the GraphQlRepository annotation and QueryByExampleExecutor interface instead of a long list declared
methods.

Check out [traditional vs QBE approach](https://github.com/danvega/graphql-qbe/blob/main/README.md)

## Project Requirements

- Java 21
- Maven 3.9.x
- PostgreSQL 16

## Running the Application

1. Start the application:

```bash
./mvnw spring-boot:run
```

2. Access GraphiQL interface at: http://localhost:8080/graphiql

## GraphQL API Usage

The API supports the following queries:

### Query all authors

```graphql
query{
  allAuthors {
    id
    firstName
    lastName
    username
  }
}
```

### Query author by ID

```graphql
query{ authorById(id: 5) {
    id
    firstName
    lastName
    username
  }
}
```

### Query authors using First and Last name

```graphql
query{
  authors(authorInput:  {
     lastName: "Verne"
     firstName: "Jules"
  }) {
    id
    firstName
    lastName
    username
  }
}
```

### Multiple tests and queries
[AuthorRepository tests](src/test/java/com/erickson/graphql_db/repository/AuthorRepositoryTest.java)
* findAll
* findById
* findByFirstAndLastName
* findByFirstName
* findByUsername
