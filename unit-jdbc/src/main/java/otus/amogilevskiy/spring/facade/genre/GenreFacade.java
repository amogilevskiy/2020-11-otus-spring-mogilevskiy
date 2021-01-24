package otus.amogilevskiy.spring.facade.genre;

import otus.amogilevskiy.spring.domain.Genre;

import java.util.Optional;

public interface GenreFacade {

    Optional<Genre> findOrCreateGenre();

}
