package com.example.demo;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    // Maps to: query { books { ... } }
    @QueryMapping
    public List<Book> books() {
        return repository.findAll();
    }

    // Maps to: query { bookById(id: 1) { ... } }
    @QueryMapping
    public Book bookById(@Argument Long id) {
        return repository.findById(id).orElse(null);
    }

    // Maps to: mutation { addBook(title: "...", author: "...", pages: 100) { ... } }
    @MutationMapping
    public Book addBook(@Argument String title, @Argument String author, @Argument int pages) {
        return repository.save(new Book(title, author, pages));
    }

    // Maps to: mutation { deleteBook(id: 1) }
    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
