package otus.amogilevskiy.spring.utils;

import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.domain.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestData {

    public static final String FIRST_AUTHOR_ID = "1";
    public static final String SECOND_AUTHOR_ID = "2";
    public static final String FIRST_COMMENT_ID = "1";
    public static final String SECOND_COMMENT_ID = "2";
    public static final String FIRST_BOOK_ID = "1";
    public static final String SECOND_BOOK_ID = "2";
    public static final String FIRST_GENRE_ID = "1";

    public static Genre firstGenre() {
        return new Genre("1", "IT");
    }

    public static Author firstAuthor() {
        return new Author(FIRST_AUTHOR_ID, "William", "Shakespeare", null);
    }

    public static Author anotherAuthor() {
        return new Author(SECOND_AUTHOR_ID, "Lev", "Tolstoy", "Nikolayevich");
    }

    public static Book firstBook() {
        return new Book(FIRST_BOOK_ID, "Java 11", firstGenre(), Set.of(firstAuthor()));
    }

    public static Book secondBook() {
        return new Book(SECOND_BOOK_ID, "Swift", firstGenre(), Set.of(firstAuthor()));
    }

    public static List<Comment> firstBookComments() {
        return List.of(
                new Comment("1", "First comment", firstBook()),
                new Comment("2", "Second comment", firstBook())
        );
    }

    public static List<Comment> secondBookComments() {
        return List.of(
                new Comment("3", "Third comment", secondBook())
        );
    }

    public static List<Comment> allComments() {
        return Stream.concat(firstBookComments().stream(), secondBookComments().stream())
                .collect(Collectors.toList());
    }

    public static List<Book> allBooks() {
        return List.of(
                firstBook(),
                new Book(SECOND_BOOK_ID, "Swift", firstGenre(), Set.of(firstAuthor())),
                new Book("3", "Kotlin", firstGenre(), Set.of(anotherAuthor()))
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
