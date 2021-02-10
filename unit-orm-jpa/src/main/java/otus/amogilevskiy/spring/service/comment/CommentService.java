package otus.amogilevskiy.spring.service.comment;

import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CreateCommentDto;
import otus.amogilevskiy.spring.dto.comment.UpdateCommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> create(CreateCommentDto dto);

    boolean deleteById(long id);

    boolean deleteAllByBookId(long bookId);

    boolean update(UpdateCommentDto dto);

    List<Comment> findAllByBookId(long bookId);

}
