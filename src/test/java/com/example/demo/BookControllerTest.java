package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;

@SpringBootTest
@AutoConfigureGraphQlTester
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldReturnSeededBooks() {
        graphQlTester.document("{ books { id title author pages } }")
            .execute()
            .path("books")
            .entityList(Object.class)
            .hasSizeGreaterThan(0);
    }

    @Test
    void shouldAddAndReturnNewBook() {
        graphQlTester.document("""
                mutation {
                    addBook(title: "Test Book", author: "Tester", pages: 123) {
                        title
                        author
                        pages
                    }
                }
                """)
            .execute()
            .path("addBook.title").entity(String.class).isEqualTo("Test Book")
            .path("addBook.pages").entity(Integer.class).isEqualTo(123);
    }
}
