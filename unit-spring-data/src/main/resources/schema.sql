DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

CREATE TABLE authors
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    middle_name VARCHAR(255)
);

CREATE TABLE genres
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) UNIQUE
);

CREATE TABLE books
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    title    VARCHAR(255),
    genre_id BIGINT NOT NULL,
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE comments
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    text    VARCHAR(255),
    book_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books (id)
);

CREATE TABLE book_authors
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors (id)
);
