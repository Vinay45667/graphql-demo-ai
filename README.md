# Spring Boot: GraphQL + Security + JPA + H2

A demo Spring Boot app that exposes a GraphQL API for managing books, persists
them to an in-memory H2 database via Spring Data JPA, and protects endpoints
with Spring Security. CI runs on every push via GitHub Actions.

## Dependencies included

- Spring Web
- Spring for GraphQL
- Spring Security
- Spring Data JPA
- H2 Database

Built on Spring Boot 3.5.x and Java 21.

## Project layout

```
src/main/java/com/example/demo/
├── DemoApplication.java    app entry point + seed data
├── Book.java               JPA entity
├── BookRepository.java     Spring Data JPA repository
├── BookController.java     GraphQL queries and mutations
├── HelloController.java    public + secured REST endpoints
└── SecurityConfig.java     Spring Security setup
src/main/resources/
├── application.properties  H2, JPA, GraphQL config
└── graphql/schema.graphqls GraphQL schema
```

## Run it locally

```bash
mvn spring-boot:run
```

Then open these in your browser:

- http://localhost:8080/ — public page, no login
- http://localhost:8080/graphiql — GraphQL playground (try the queries below)
- http://localhost:8080/h2-console — database console
  (JDBC URL: `jdbc:h2:mem:booksdb`, user: `sa`, no password)
- http://localhost:8080/secure — requires login (user: `user`, password: `password`)

## Try these GraphQL operations in GraphiQL

Query all books:

```graphql
query {
  books {
    id
    title
    author
    pages
  }
}
```

Add a book:

```graphql
mutation {
  addBook(title: "Domain-Driven Design", author: "Eric Evans", pages: 560) {
    id
    title
  }
}
```

Fetch one by id:

```graphql
query {
  bookById(id: 1) {
    title
    author
  }
}
```

Delete a book:

```graphql
mutation {
  deleteBook(id: 1)
}
```

## Push to GitHub and trigger CI

Create an empty repo on GitHub (no README/license), then:

```bash
git init
git add .
git commit -m "Initial commit"
git branch -M master
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git
git push -u origin master
```

The push triggers the workflow. Watch it under the Actions tab. The workflow
also has a manual "Run workflow" button enabled via workflow_dispatch.

## Note on local builds behind a corporate network

If `mvn` fails locally with a PKIX / certificate error, that is your network
intercepting HTTPS, not a project problem. The GitHub Actions build runs on a
clean network and is unaffected, so you can rely on CI while sorting out the
local certificate with your IT team.
