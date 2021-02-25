package otus.amogilevskiy.spring.repository.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public boolean contains(String title) {
        var query = em.createQuery("select count(g) from Genre g where upper(g.title) = upper(:title) ", Long.class);
        query.setParameter("title", title);
        return query.getSingleResult() > 0;
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        var query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return Optional.of(genre);
        } else {
            return Optional.of(em.merge(genre));
        }
    }

    @Override
    public boolean delete(Genre genre) {
        em.remove(genre);
        return true;
    }

}
