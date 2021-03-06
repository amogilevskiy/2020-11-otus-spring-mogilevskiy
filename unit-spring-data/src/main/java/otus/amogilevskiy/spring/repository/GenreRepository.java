package otus.amogilevskiy.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
