package otus.amogilevskiy.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.repository.BookRepository;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;
import otus.amogilevskiy.spring.service.genre.GenreService;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Resource.BOOK));
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Book create(BookDto dto) {
        var genre = genreService.findById(dto.getGenreId());
        var authors = authorService.findAllById(dto.getAuthorIds());
        return bookRepository.save(Book.builder()
                .title(dto.getTitle())
                .genre(genre)
                .authors(new HashSet<>(authors))
                .build()
        );
    }

    @Transactional
    @Override
    public Book updateById(long id, BookDto dto) {
        var book = findById(id);
        book.setTitle(dto.getTitle());
        book.setGenre(genreService.findById(dto.getGenreId()));
        var authors = book.getAuthors();
        var newAuthors = authorService.findAllById(dto.getAuthorIds());

        var authorsToRemove = authors.stream()
                .filter(author -> !newAuthors.contains(author))
                .collect(Collectors.toSet());

        authorsToRemove.forEach(author -> author.removeBook(book));

        newAuthors.stream()
                .filter(author -> !authors.contains(author))
                .forEach(author -> author.addBook(book));

        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        bookRepository.deleteById(id);
        return true;
    }

}
