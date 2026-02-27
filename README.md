# Spring Boot GraphQL

A Spring Boot and GraphQL application inspired by Dan Vega's tutorial on simplifying GraphQL by using Query By Example.

The exciting part is there is no need for a Controller, Service or Data Mappers.
Plus the three repositories are empty!

GraphQL uses the GraphQlRepository annotation and QueryByExampleExecutor interface instead of a long list declared
methods.

Check out Dan's code and documentation
of [traditional vs QBE approach](https://github.com/danvega/graphql-qbe/blob/main/README.md)

## Database


## Project Requirements

- Java 21
- Maven 3.9.x
- PostgreSQL 17

## Running the Application

1. Start the application:

```bash
mvn spring-boot:run
```

2. Access GraphiQL interface at: http://localhost:8080/graphiql

## GraphQL API Usage

The API supports the following queries:

### Query all authors

```graphql
query{
  allAuthors {
    authorId
    firstName
    lastName
    username
  }
}
```

### Query author by ID

```graphql
{
  authorById(authorId: 5) {
    authorId
    firstName
    lastName
    username
  }
}
```

### Query authors using First and Last name

```graphql
{
  authors(authorInput: {lastName: "Verne", firstName: "Jules"}) {
    authorId
    firstName
    lastName
    username
  }
}
```

### Query authors and books using author id

```graphql
{
  authorById(authorId: 2) {
    firstName
    lastName
    books {
      bookId
      title
      publishedOn
    }
  }
}
``` 

### Query post and comments using post id

```graphql
{
  bookById(bookId: 3) {
    bookId
    title
    publishedOn
    
    comments {
      name
      content
      publishedOn
      updatedOn
    }
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
* findByEmail

### Gotcha

```graphql
{
  authorById(authorId: 2) {
    firstName
    lastName
    books {
      bookId
      title
      comments {
        name
      }
    }
  }
}
```

Fails due to org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.loader.MultipleBagFetchException:
cannot simultaneously fetch multiple
bags: [com.erickson.graphql_db.model.BookEntity.comments, com.erickson.graphql_db.model.AuthorEntity.books]

There are various solutions for handling this Hibernate error.  
See [Spring Data Jpa Multiplebagfetchexception](https://www.springcloud.io/post/2022-06/spring-data-jpa-multiplebagfetchexception)

The point is GraphQL can not magically drill down through multiple child tables using generated methods in a repository.
Handling this use case requires a Controller, a Service and some thoughtful code.
