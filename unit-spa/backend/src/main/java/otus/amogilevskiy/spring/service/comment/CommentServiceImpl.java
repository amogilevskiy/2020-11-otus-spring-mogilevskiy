package otus.amogilevskiy.spring.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.repository.CommentRepository;
import otus.amogilevskiy.spring.service.book.BookService;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookService bookService;
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Resource.COMMENT));
    }

    @Transactional
    @Override
    public Comment updateById(long id, CommentDto dto) {
        var comment = findById(id);
        comment.setText(dto.getText());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment create(CommentDto dto) {
        var book = bookService.findById(dto.getBookId());
        var comment = Comment.builder()
                .text(dto.getText())
                .book(book)
                .build();
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        commentRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Comment> findByBookId(long bookId, Pageable pageable) {
        return commentRepository.findByBook_Id(bookId, pageable);
    }
}
