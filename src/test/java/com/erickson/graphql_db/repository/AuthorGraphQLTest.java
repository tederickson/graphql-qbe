package com.erickson.graphql_db.repository;

import com.erickson.graphql_db.model.AuthorEntity;
import com.erickson.graphql_db.model.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureHttpGraphQlTester
class AuthorGraphQLTest {
    @Autowired
    private HttpGraphQlTester graphQlTester;

    @Test
    void findAll() {
        String query = """
                    query {
                        allAuthors {
                            authorId
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
                         authorId
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
                .authorId(5L)
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
                         authorId
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
                .authorId(5L)
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
    void findByEmail() {
        String query = """
                    query{
                       authors(authorInput:  {
                            email: "alex@test.net"
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
                .firstName("Alexandre")
                .lastName("Dumas")
                .username("alexandre")
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
                         authorId
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
                       authorById(authorId: 3) {
                         authorId
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
                .authorId(3L)
                .firstName("Jane")
                .lastName("Austen")
                .username("jane")
                .email("jane@test.net")
                .build();

        assertEquals(expected, authorEntity);
    }

    @Test
    void findById_IncludeBooks() {
        String query = """
                    query{ authorById(authorId: 2) {
                          firstName
                          lastName
                          books {
                            bookId
                            title
                            publishedOn
                          }
                        }
                      }
                """;

        AuthorEntity authorEntity = graphQlTester.document(query)
                .execute()
                .path("data.authorById")
                .entity(AuthorEntity.class)
                .get();

        assertEquals("Alexandre", authorEntity.getFirstName());
        assertEquals("Dumas", authorEntity.getLastName());
        assertNull(authorEntity.getEmail());
        assertNull(authorEntity.getUsername());
        assertNull(authorEntity.getAuthorId());
        assertEquals(3, authorEntity.getBooks().size());

        for (BookEntity bookEntity : authorEntity.getBooks()) {
            final long postId = bookEntity.getBookId();
            String expectedDate;

            if (postId == 1L) {
                assertEquals("The Three Musketeers", bookEntity.getTitle());
                expectedDate = "1844-01-25";
            }
            else if (postId == 2L) {
                assertEquals("The Count of Monte Cristo", bookEntity.getTitle());
                expectedDate = "1844-08-05";
            }
            else if (postId == 3L) {
                assertEquals("The Man in the Iron Mask", bookEntity.getTitle());
                expectedDate = "1847-12-05";
            }
            else {
                throw new IllegalStateException("Unexpected value: " + bookEntity);
            }

            LocalDate publishedOn = LocalDate.parse(expectedDate);

            assertEquals(publishedOn, bookEntity.getPublishedOn());
        }
    }

    @Test
    void findById_NoBooks() {
        String query = """
                    query{ authorById(authorId: 1) {
                          firstName
                          lastName
                          books {
                            bookId
                            title
                            publishedOn
                          }
                        }
                      }
                """;

        AuthorEntity authorEntity = graphQlTester.document(query)
                .execute()
                .path("data.authorById")
                .entity(AuthorEntity.class)
                .get();

        assertEquals("Agatha", authorEntity.getFirstName());
        assertEquals("Christie", authorEntity.getLastName());
        assertNull(authorEntity.getEmail());
        assertNull(authorEntity.getUsername());
        assertNull(authorEntity.getAuthorId());
        assertEquals(0, authorEntity.getBooks().size());
    }
}
