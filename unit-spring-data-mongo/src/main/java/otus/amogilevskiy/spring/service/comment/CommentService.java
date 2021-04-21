package otus.amogilevskiy.spring.service.comment;

import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {

    boolean create(CommentDto dto);

    List<Comment> findAll();

    List<Comment> findAllByBookId(String bookId);

}
