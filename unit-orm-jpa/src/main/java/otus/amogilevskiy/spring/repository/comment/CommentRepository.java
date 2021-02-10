package otus.amogilevskiy.spring.repository.comment;

import otus.amogilevskiy.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long bookId);

    List<Comment> findAll();

    boolean updateTextById(long id, String text);

    boolean deleteAllByBookId(long bookId);

    boolean deleteById(long id);

    Optional<Comment> save(Comment comment);

}
