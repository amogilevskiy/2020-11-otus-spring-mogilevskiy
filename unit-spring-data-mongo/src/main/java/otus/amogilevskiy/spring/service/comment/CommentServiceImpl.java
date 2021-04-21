package otus.amogilevskiy.spring.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.repository.BookRepository;
import otus.amogilevskiy.spring.repository.CommentRepository;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final LocalizationService localizationService;

    @Override
    public boolean create(CommentDto dto) {
        var book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException(localizationService.localize("error.bookNotFound")));
        var comment = Comment.builder()
                .text(dto.getText())
                .book(book)
                .build();
        commentRepository.save(comment);
        return true;
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findAllByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }
}
