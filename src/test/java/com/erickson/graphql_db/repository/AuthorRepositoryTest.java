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
    void testFindAll() {
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
    void testFindJulesVerne() {
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
        assertEquals(5, authorEntity.getId());
        assertEquals("Jules", authorEntity.getFirstName());
        assertEquals("Verne", authorEntity.getLastName());
    }

    @Test
    void testFindJules() {
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
        assertEquals(5, authorEntity.getId());
        assertEquals("Jules", authorEntity.getFirstName());
        assertEquals("Verne", authorEntity.getLastName());
    }

    @Test
    void testFindCharles() {
        String query = """
                    query{
                       authors(authorInput:  {
                            username: "charles"
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
        assertEquals(4, authorEntity.getId());
        assertEquals("Charles", authorEntity.getFirstName());
        assertEquals("Dickens", authorEntity.getLastName());
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

        var list = graphQlTester.document(query)
                .execute()
                .path("data.authors")
                .entityList(AuthorEntity.class)
                .hasSize(0);
    }


    @Test
    void testFindById() {
        String query = """
                    query{
                       authorById(id: 3) {
                         id
                         firstName
                         lastName
                         username
                       }
                     }
                """;

        AuthorEntity authorEntity = graphQlTester.document(query)
                .execute()
                .path("data.authorById")
                .entity(AuthorEntity.class)
                .get();

        assertEquals(3, authorEntity.getId());
        assertEquals("Jane", authorEntity.getFirstName());
        assertEquals("Austen", authorEntity.getLastName());
    }
}