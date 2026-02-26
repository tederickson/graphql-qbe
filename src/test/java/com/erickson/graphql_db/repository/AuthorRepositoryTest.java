package com.erickson.graphql_db.repository;

import com.erickson.graphql_db.model.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private HttpGraphQlTester graphQlTester;

    @Test
    void findAll() {
        String query = """
                    query {
                        allAuthors {
                            id
                            firstName
                            lastName
                            username
                        }
                    }
                """;

        graphQlTester.document(query)
                .execute()
                .path("data.allAuthors")
                .entityList(AuthorEntity.class)
                .hasSize(6);
    }

    @Test
    void findByFirstAndLastName() {
        String query = """
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
                """;

        var list = graphQlTester.document(query)
                .execute()
                .path("data.authors")
                .entityList(AuthorEntity.class)
                .hasSize(1);

        AuthorEntity authorEntity = list.get().getFirst();

        AuthorEntity expected = AuthorEntity.builder()
                .id(5L)
                .firstName("Jules")
                .lastName("Verne")
                .username("jules")
                .build();

        assertEquals(expected, authorEntity);
    }

    @Test
    void findByFirstName() {
        String query = """
                    query{
                       authors(authorInput:  {
                            firstName: "Jules"
                       }) {
                         id
                         firstName
                         lastName
                         username
                       }
                     }
                """;

        var list = graphQlTester.document(query)
                .execute()
                .path("data.authors")
                .entityList(AuthorEntity.class)
                .hasSize(1);

        AuthorEntity authorEntity = list.get().getFirst();
        AuthorEntity expected = AuthorEntity.builder()
                .id(5L)
                .firstName("Jules")
                .lastName("Verne")
                .username("jules")
                .build();

        assertEquals(expected, authorEntity);
    }

    @Test
    void findByUsername() {
        String query = """
                    query{
                       authors(authorInput:  {
                            username: "charles"
                       }) {
                         firstName
                         lastName
                         username
                       }
                     }
                """;

        AuthorEntity authorEntity = graphQlTester.document(query)
                .execute()
                .path("data.authors")
                .entityList(AuthorEntity.class)
                .hasSize(1)
                .get().getFirst();

        AuthorEntity expected = AuthorEntity.builder()
                .firstName("Charles")
                .lastName("Dickens")
                .username("charles")
                .build();

        assertEquals(expected, authorEntity);
    }

    @Test
    void testNoMatch() {
        String query = """
                    query{
                       authors(authorInput:  {
                            firstName: "AbraCadabra"
                       }) {
                         id
                         firstName
                         lastName
                         username
                       }
                     }
                """;

        graphQlTester.document(query)
                .execute()
                .path("data.authors")
                .entityList(AuthorEntity.class)
                .hasSize(0);
    }

    @Test
    void findById() {
        String query = """
                    query{
                       authorById(id: 3) {
                         id
                         firstName
                         lastName
                         username
                         email
                       }
                     }
                """;

        AuthorEntity authorEntity = graphQlTester.document(query)
                .execute()
                .path("data.authorById")
                .entity(AuthorEntity.class)
                .get();

        AuthorEntity expected = AuthorEntity.builder()
                .id(3L)
                .firstName("Jane")
                .lastName("Austen")
                .username("jane")
                .email("jane@test.net")
                .build();

        assertEquals(expected, authorEntity);
    }
}