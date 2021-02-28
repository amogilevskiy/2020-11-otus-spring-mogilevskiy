package otus.amogilevskiy.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
