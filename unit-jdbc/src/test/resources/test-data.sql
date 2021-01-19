INSERT INTO authors (id, `first_name`, `last_name`, `middle_name`)
values (1, 'Test 1', 'with', 'middle name');

INSERT INTO authors (id, `first_name`, `last_name`)
values (2, 'Test 2', 'Only last name');


INSERT INTO genres (id, `title`)
values (1, 'IT');


INSERT INTO books (id, `title`, `author_id`, `genre_id`)
values (1, 'Java 11', 1, 1);

INSERT INTO books (id, `title`, `author_id`, `genre_id`)
values (2, 'Swift', 1, 1);

INSERT INTO books (id, `title`, `author_id`, `genre_id`)
values (3, 'Kotlin', 2, 1);






