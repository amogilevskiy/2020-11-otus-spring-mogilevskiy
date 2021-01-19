package otus.amogilevskiy.spring.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenreDao implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Genre> findById(long id) {
        var params = Map.of("id", id);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM genres WHERE `id` = :id", params, new GenreMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean create(Genre genre) {
        var params = Map.of(
                "id", genre.getId(),
                "title", genre.getTitle()
        );
        try {
            return jdbc.update("INSERT INTO genres (`id`, `title`) values (:id, :title)", params) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(
                    resultSet.getLong("id"),
                    resultSet.getString("title")
            );
        }
    }

}
