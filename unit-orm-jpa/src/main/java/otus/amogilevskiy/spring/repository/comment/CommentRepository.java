package otus.amogilevskiy.spring.repository.comment;

import otus.amogilevskiy.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    boolean delete(Comment comment);

    Optional<Comment> save(Comment comment);

}
