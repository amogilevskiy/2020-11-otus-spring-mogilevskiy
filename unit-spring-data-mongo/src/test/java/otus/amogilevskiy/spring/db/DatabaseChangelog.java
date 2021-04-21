package otus.amogilevskiy.spring.db;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.repository.AuthorRepository;
import otus.amogilevskiy.spring.repository.BookRepository;
import otus.amogilevskiy.spring.repository.CommentRepository;
import otus.amogilevskiy.spring.repository.GenreRepository;

import java.util.Set;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "authors", author = "amogilevskiy")
    public void authors(AuthorRepository authorRepository) {
        authorRepository.save(new Author("1", "William", "Shakespeare", null));
        authorRepository.save(new Author("2", "Lev", "Tolstoy", "Nikolayevich"));
    }

    @ChangeSet(order = "002", id = "genres", author = "amogilevskiy")
    public void genres(GenreRepository genreRepository) {
        genreRepository.save(new Genre("1", "IT"));
    }

    @ChangeSet(order = "003", id = "books", author = "amogilevskiy")
    public void books(BookRepository bookRepository,
                      AuthorRepository authorRepository,
                      GenreRepository genreRepository) {
        bookRepository.save(
                new Book(
                        "1",
                        "Java 11",
                        genreRepository.findById("1").orElseThrow(),
                        Set.of(authorRepository.findById("1").orElseThrow())
                )
        );

        bookRepository.save(
                new Book(
                        "2",
                        "Swift",
                        genreRepository.findById("1").orElseThrow(),
                        Set.of(authorRepository.findById("1").orElseThrow())
                )
        );

        bookRepository.save(
                new Book(
                        "3",
                        "Kotlin",
                        genreRepository.findById("1").orElseThrow(),
                        Set.of(authorRepository.findById("2").orElseThrow())
                )
        );
    }

    @ChangeSet(order = "004", id = "comments", author = "amogilevskiy")
    public void comments(BookRepository bookRepository, CommentRepository commentRepository) {
        var firstBook = bookRepository.findById("1").orElseThrow();
        commentRepository.save(new Comment("1", "First comment", firstBook));
        commentRepository.save(new Comment("2", "Second comment", firstBook));

        var secondBook = bookRepository.findById("2").orElseThrow();
        commentRepository.save(new Comment("3", "Third comment", secondBook));
    }
}