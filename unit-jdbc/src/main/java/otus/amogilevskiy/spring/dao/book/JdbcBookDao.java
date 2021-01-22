package otus.amogilevskiy.spring.dao.book;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.service.book.BookDetail;

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
                    jdbc.queryForObject("SELECT * FROM books WHERE id = :id", params, new BookMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BookDetail> findDetailById(long id) {
        var params = Map.of("id", id);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT b.id, b.title, a.id, a.first_name, a.last_name, a.middle_name, g.id, g.title FROM books AS b " +
                                    "INNER JOIN authors as a on b.author_id = a.id " +
                                    "INNER JOIN genres as g on b.genre_id = g.id " +
                                    "WHERE b.id = :id", params, new BookDetailMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return jdbc.query("SELECT * FROM books", new BookMapper());
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public List<Book> findAllByAuthorId(long authorId) {
        var params = Map.of(
                "authorId", authorId
        );
        try {
            return jdbc.query("SELECT * FROM books WHERE author_id = :authorId", params, new BookMapper());
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public boolean create(Book book) {
        var params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "authorId", book.getAuthorId(),
                "genreId", book.getGenreId()
        );
        try {
            return jdbc.update("INSERT INTO books (id, title, author_id, genre_id) values (:id, :title, :authorId, :genreId)", params) > 0;
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
            var authorId = resultSet.getLong("author_id");
            var genreId = resultSet.getLong("genre_id");
            return new Book(
                    resultSet.getLong("id"),
                    resultSet.getString("title"),
                    authorId == 0 ? null : authorId,
                    genreId == 0 ? null : genreId
            );
        }
    }

    private static class BookDetailMapper implements RowMapper<BookDetail> {

        @Override
        public BookDetail mapRow(ResultSet resultSet, int i) throws SQLException {
            return new BookDetail(
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
