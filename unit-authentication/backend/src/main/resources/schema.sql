DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS book_authors;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;

CREATE TABLE authors
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    middle_name VARCHAR(255)
);

CREATE TABLE genres
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE
);

CREATE TABLE books
(
    id       BIGSERIAL PRIMARY KEY,
    title    VARCHAR(255),
    genre_id BIGINT NOT NULL,
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE comments
(
    id      BIGSERIAL PRIMARY KEY,
    text    VARCHAR(255),
    book_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books (id)
);

CREATE TABLE book_authors
(
    id        BIGSERIAL PRIMARY KEY,
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors (id)
);
