package otus.amogilevskiy.spring.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenreDao implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public boolean contains(String title) {
        var params = Map.of("title", title);
        try {
            return jdbc.query("SELECT 1 where EXISTS(SELECT id from genres where title = :title)", params, ResultSet::next);
        } catch (DataAccessException e) {
            return false;
        }
    }

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
    public List<Genre> findAll() {
        try {
            return jdbc.query("SELECT id, title FROM genres", new GenreMapper());
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Long> create(Genre genre) {
        var params = Map.of(
                "title", genre.getTitle()
        );
        var keyHolder = new GeneratedKeyHolder();
        var paramsSource = new MapSqlParameterSource(params);
        try {
            jdbc.update("INSERT INTO genres (title) values (:title)", paramsSource, keyHolder);
            return Optional.of(keyHolder.getKey().longValue());
        } catch (Exception e) {
            return Optional.empty();
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
