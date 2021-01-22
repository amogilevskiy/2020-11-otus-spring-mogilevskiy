package otus.amogilevskiy.spring.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.dto.genre.CreateGenreDto;

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
                    jdbc.queryForObject("SELECT id, title FROM genres WHERE id = :id", params, new GenreMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> findByTitle(String title) {
        var params = Map.of("title", title);
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT id, title FROM genres WHERE UPPER(title) = UPPER(:title)", params, new GenreMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean create(CreateGenreDto dto) {
        var params = Map.of(
                "title", dto.getTitle()
        );
        try {
            return jdbc.update("INSERT INTO genres (title) values (:title)", params) > 0;
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
