INSERT INTO authors (id, first_name, last_name)
values (1, 'William', 'Shakespeare');

INSERT INTO authors (id, first_name, last_name, middle_name)
values (2, 'Lev', 'Tolstoy', 'Nikolayevich');


INSERT INTO genres (id, title)
values (1, 'IT');


INSERT INTO books (id, title, genre_id)
values (1, 'Java 11', 1);

INSERT INTO books (id, title, genre_id)
values (2, 'Swift', 1);

INSERT INTO books (id, title, genre_id)
values (3, 'Kotlin', 1);


INSERT INTO comments (id, text, book_id)
values (1, 'test text', 1);

INSERT INTO comments (id, text, book_id)
values (2, 'test text 2', 1);

INSERT INTO comments (id, text, book_id)
values (3, 'test text 3', 3);


INSERT INTO book_authors (id, book_id, author_id)
values (1, 1, 1);

INSERT INTO book_authors (id, book_id, author_id)
values (2, 2, 1);

INSERT INTO book_authors (id, book_id, author_id)
values (3, 3, 2);