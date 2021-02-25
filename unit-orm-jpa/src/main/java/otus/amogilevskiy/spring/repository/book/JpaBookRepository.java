package otus.amogilevskiy.spring.repository.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        var query = em.createQuery("select b from Book b", Book.class);
        var graph = em.getEntityGraph("book-genre-author-graph");
        query.setHint("javax.persistence.fetchgraph", graph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return Optional.of(book);
        } else {
            return Optional.of(em.merge(book));
        }
    }

    @Override
    public boolean deleteById(long id) {
        findById(id).ifPresent(em::remove);
        return true;
    }

}
