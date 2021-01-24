package otus.amogilevskiy.spring.dao.book;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JdbcBookDao implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        var params = Map.of("id", id);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT b.id, b.title, a.id, a.first_name, a.last_name, a.middle_name, g.id, g.title FROM books AS b " +
                                    "LEFT JOIN authors as a on b.author_id = a.id " +
                                    "LEFT JOIN genres as g on b.genre_id = g.id " +
                                    "WHERE b.id = :id", params, new BookMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAllByTitle(String title) {
        var params = Map.of("title", title);
        try {
            return jdbc.query("SELECT b.id, b.title, a.id, a.first_name, a.last_name, a.middle_name, g.id, g.title FROM books AS b " +
                    "LEFT JOIN authors as a on b.author_id = a.id " +
                    "LEFT JOIN genres as g on b.genre_id = g.id WHERE UPPER(b.title) = UPPER(:title)", params, new BookMapper());
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return jdbc.query("SELECT b.id, b.title, a.id, a.first_name, a.last_name, a.middle_name, g.id, g.title FROM books AS b " +
                    "LEFT JOIN authors as a on b.author_id = a.id " +
                    "LEFT JOIN genres as g on b.genre_id = g.id", new BookMapper());
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public List<Book> findAllByAuthorId(long authorId) {
        var params = Map.of(
                "author_id", authorId
        );
        try {
            return jdbc.query("SELECT b.id, b.title, a.id, a.first_name, a.last_name, a.middle_name, g.id, g.title FROM books AS b " +
                    "LEFT JOIN authors as a on b.author_id = a.id " +
                    "LEFT JOIN genres as g on b.genre_id = g.id WHERE b.author_id = :author_id", params, new BookMapper());
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public boolean create(Book book) {
        var params = Map.of(
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()
        );
        try {
            return jdbc.update("INSERT INTO books (title, author_id, genre_id) values (:title, :author_id, :genre_id)", params) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean update(Book book) {
        var params = Map.of(
                "id", book.getId(),
                "title", book.getTitle()
        );
        try {
            return jdbc.update("UPDATE books SET title = :title WHERE id = :id", params) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(long id) {
        var params = Map.of(
                "id", id
        );
        try {
            return jdbc.update("DELETE FROM books WHERE id = :id", params) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(
                    resultSet.getLong("books.id"),
                    resultSet.getString("books.title"),
                    new Author(
                            resultSet.getLong("authors.id"),
                            resultSet.getString("authors.first_name"),
                            resultSet.getString("authors.last_name"),
                            resultSet.getString("authors.middle_name")
                    ),
                    new Genre(
                            resultSet.getLong("genres.id"),
                            resultSet.getString("genres.title")
                    )
            );
        }
    }

}
