package otus.amogilevskiy.spring.service.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {

    Comment findById(long id);

    Comment create(CommentDto dto);

    boolean deleteById(long id);

    Comment updateById(long id, CommentDto dto);

    Page<Comment> findByBookId(long bookId, Pageable pageable);

    List<Comment> findAll();

}
