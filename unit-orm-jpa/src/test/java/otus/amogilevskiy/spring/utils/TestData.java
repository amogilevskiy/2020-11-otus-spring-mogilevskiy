package otus.amogilevskiy.spring.utils;

import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Genre;

import java.util.List;
import java.util.Set;

public class TestData {

    public static final long FIRST_AUTHOR_ID = 1;
    public static final long SECOND_AUTHOR_ID = 2;

    public static long NUMBER_OF_ALL_AUTHORS = 2;

    public static final long FIRST_COMMENT_ID = 1;
    public static final long SECOND_COMMENT_ID = 2;

    public static final long FIRST_BOOK_ID = 1;
    public static final long SECOND_BOOK_ID = 2;

    public static final long FIRST_GENRE_ID = 1;

    public static long NUMBER_OF_ALL_BOOKS = 3;

    public static long NUMBER_OF_ALL_GENRES = 1;

    public static long NUMBER_OF_ALL_COMMENTS = 3;

    public static Genre firstGenre() {
        return new Genre(1L, "IT");
    }

    public static Author firstAuthor() {
        return new Author(FIRST_AUTHOR_ID, "Test 1", "with", "middle name");
    }

    public static Author anotherAuthor() {
        return new Author(SECOND_AUTHOR_ID, "Test 2", "Only last name", null);
    }

    public static Book firstBook() {
        return new Book(FIRST_BOOK_ID, "Java 11", Set.of(firstAuthor()), firstGenre());
    }

    public static List<Book> allBooks() {
        return List.of(
                firstBook(),
                new Book(SECOND_BOOK_ID, "Swift", Set.of(firstAuthor()), firstGenre()),
                new Book(3L, "Kotlin", Set.of(anotherAuthor()), firstGenre())
        );
    }

    public static List<Genre> allGenres() {
        return List.of(
                firstGenre()
        );
    }

    public static List<Author> allAuthors() {
        return List.of(
                firstAuthor(),
                anotherAuthor()
        );
    }

}
