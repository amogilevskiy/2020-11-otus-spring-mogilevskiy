package otus.amogilevskiy.spring.dao.author;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorDao implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Author> findById(long id) {
        var params = Map.of("id", id);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM authors WHERE `id` = :id", params, new AuthorMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        try {
            return jdbc.query("SELECT * FROM authors", new AuthorMapper());
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public boolean create(Author author) {
        var params = new HashMap<>(Map.of(
                "id", author.getId(),
                "first_name", author.getFirstName(),
                "last_name", author.getLastName()
        ));

        params.put("middle_name", author.getMiddleName());

        try {
            return jdbc.update("INSERT INTO authors (`id`, `first_name`, `last_name`, `middle_name`) values (:id, :first_name, :last_name, :middle_name)", params) > 0;
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
            return jdbc.update("DELETE FROM authors WHERE `id` = :id", params) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("middle_name")
            );
        }
    }
}
