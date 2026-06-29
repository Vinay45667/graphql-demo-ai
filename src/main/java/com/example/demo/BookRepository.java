package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA generates the implementation automatically:
// findAll, findById, save, deleteById, count, etc. all come for free.
public interface BookRepository extends JpaRepository<Book, Long> {
}
