package otus.amogilevskiy.spring.facade.author;

import otus.amogilevskiy.spring.domain.Author;

import java.util.Optional;

public interface AuthorFacade {

    Optional<Author> findOrCreateAuthor();

}
