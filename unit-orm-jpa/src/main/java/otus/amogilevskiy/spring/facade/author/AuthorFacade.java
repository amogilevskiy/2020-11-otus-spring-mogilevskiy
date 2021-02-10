package otus.amogilevskiy.spring.facade.author;

import otus.amogilevskiy.spring.domain.Author;

import java.util.Set;

public interface AuthorFacade {

    Set<Author> findOrCreateAuthors();

}
