INSERT INTO users (username, password, enabled)
values ('admin', '$2y$10$7FecnGqGcjaSXjH6UCx/le8SquD9ROO7gi6ZtIp7xRyNXq71mnqUm', 'true');

INSERT INTO user_authorities (user_id, authority)
values (1, 'admin');

INSERT INTO authors (first_name, last_name)
values ('William', 'Shakespeare');

INSERT INTO authors (first_name, last_name, middle_name)
values ('Lev', 'Tolstoy', 'Nikolayevich');


INSERT INTO genres (title)
values ('IT');


INSERT INTO books (title, genre_id)
values ('Java 11', 1);

INSERT INTO books (title, genre_id)
values ('Swift', 1);

INSERT INTO books (title, genre_id)
values ('Kotlin', 1);


INSERT INTO comments (text, book_id)
values ('test text', 1);

INSERT INTO comments (text, book_id)
values ('test text 2', 1);

INSERT INTO comments (text, book_id)
values ('test text 3', 3);


INSERT INTO book_authors (book_id, author_id)
values (1, 1);

INSERT INTO book_authors (book_id, author_id)
values (2, 1);

INSERT INTO book_authors (book_id, author_id)
values (3, 2);