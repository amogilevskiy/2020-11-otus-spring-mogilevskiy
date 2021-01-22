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
    title VARCHAR(255)
);

CREATE TABLE books
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    title     VARCHAR(255),
    author_id BIGINT NOT NULL,
    genre_id  BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

