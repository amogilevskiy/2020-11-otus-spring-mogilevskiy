package otus.amogilevskiy.spring.repository.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return Optional.of(comment);
        } else {
            return Optional.of(em.merge(comment));
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAllByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :book_id", Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public boolean updateTextById(long id, String text) {
        Query query = em.createQuery("update Comment c " +
                "set c.text = :text " +
                "where c.id = :id");
        query.setParameter("text", text);
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean deleteAllByBookId(long bookId) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.book.id = :book_id");
        query.setParameter("book_id", bookId);
        query.executeUpdate();
        return true;
    }
}
