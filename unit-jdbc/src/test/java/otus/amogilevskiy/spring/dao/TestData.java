package otus.amogilevskiy.spring.dao;

import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Genre;

import java.util.List;

public class TestData {

    public static Genre firstGenre() {
        return new Genre(1L, "IT");
    }

    public static Author firstAuthor() {
        return new Author(1L, "Test 1", "with", "middle name");
    }

    public static Author anotherAuthor() {
        return new Author(2L, "Test 2", "Only last name", null);
    }

    public static Book firstBook() {
        return new Book(1L, "Java 11", firstAuthor(), firstGenre());
    }

    public static List<Book> allBooks() {
        return List.of(
                firstBook(),
                new Book(2L, "Swift", firstAuthor(), firstGenre()),
                new Book(3L, "Kotlin", anotherAuthor(), firstGenre())
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
