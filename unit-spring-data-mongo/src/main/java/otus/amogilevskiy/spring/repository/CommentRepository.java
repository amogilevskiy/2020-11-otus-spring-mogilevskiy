package otus.amogilevskiy.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.amogilevskiy.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String bookId);

}
