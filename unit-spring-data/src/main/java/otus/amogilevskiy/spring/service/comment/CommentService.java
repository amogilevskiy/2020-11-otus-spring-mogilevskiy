package otus.amogilevskiy.spring.service.comment;

import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> create(CommentDto dto);

    boolean deleteById(long id);

    boolean update(CommentDto dto);

    List<Comment> findAllByBookId(long bookId);

    List<Comment> findAll();

}
