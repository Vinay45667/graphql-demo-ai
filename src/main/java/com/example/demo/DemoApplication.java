package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Seeds a few books into the H2 database on startup so there is
    // something to query straight away.
    @Bean
    CommandLineRunner seedData(BookRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Book("The Pragmatic Programmer", "Hunt & Thomas", 352));
                repository.save(new Book("Clean Code", "Robert C. Martin", 464));
                repository.save(new Book("Effective Java", "Joshua Bloch", 416));
            }
        };
    }
}
