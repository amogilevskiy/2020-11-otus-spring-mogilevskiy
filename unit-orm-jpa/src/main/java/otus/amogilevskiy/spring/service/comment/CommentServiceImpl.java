package otus.amogilevskiy.spring.service.comment;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.repository.comment.CommentRepository;
import otus.amogilevskiy.spring.service.book.BookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookService bookService;
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Comment> create(CommentDto dto) {
        return bookService.findById(dto.getBookId()).flatMap(book -> commentRepository.save(Comment.builder().text(dto.getText()).book(book).build()));
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return commentRepository.findById(id).map(commentRepository::delete).orElse(false);
    }

    @Transactional
    @Override
    public boolean update(CommentDto dto) {
        return commentRepository.findById(dto.getId())
                .map(comment -> commentRepository.save(new Comment(comment.getId(), dto.getText(), comment.getBook())).isPresent())
                .orElse(false);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(long bookId) {
        var comments = bookService.findById(bookId).map(Book::getComments).orElse(List.of());
        Hibernate.initialize(comments);
        return comments;
    }
}
