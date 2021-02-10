package otus.amogilevskiy.spring.repository.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :id", Book.class);
        var graph = em.getEntityGraph("books-with-authors-genre-graph");
        query.setHint("javax.persistence.fetchgraph", graph);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        var graph = em.getEntityGraph("books-with-authors-genre-graph");
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
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean updateTitleById(long id, String title) {
        Query query = em.createQuery("update Book b " +
                "set b.title = :title " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.setParameter("title", title);
        return query.executeUpdate() > 0;
    }
}
