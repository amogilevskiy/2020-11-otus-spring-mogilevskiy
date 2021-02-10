package otus.amogilevskiy.spring.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CreateCommentDto;
import otus.amogilevskiy.spring.dto.comment.UpdateCommentDto;
import otus.amogilevskiy.spring.repository.comment.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public Optional<Comment> create(CreateCommentDto dto) {
        return commentRepository.save(new Comment(dto.getText(), new Book(dto.getBookId())));
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean deleteAllByBookId(long bookId) {
        return commentRepository.deleteAllByBookId(bookId);
    }

    @Transactional
    @Override
    public boolean update(UpdateCommentDto dto) {
        return commentRepository.findById(dto.getId())
                .map(comment -> commentRepository.save(new Comment(comment.getId(), dto.getText(), comment.getBook())).isPresent())
                .orElse(false);
    }
    
    @Override
    public List<Comment> findAllByBookId(long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }
}
