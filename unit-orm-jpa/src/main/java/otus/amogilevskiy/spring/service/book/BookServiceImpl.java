package otus.amogilevskiy.spring.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.dto.book.BookDto;
import otus.amogilevskiy.spring.repository.book.BookRepository;
import otus.amogilevskiy.spring.service.author.AuthorService;
import otus.amogilevskiy.spring.service.genre.GenreService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final LocalizationService localizationService;

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Book> create(BookDto dto) {
        var genre = genreService.findById(dto.getGenreId())
                .orElseThrow(() -> new RuntimeException(localizationService.localize("error.genreNotFound")));
        var author = authorService.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException(localizationService.localize("error.authorNotFound")));
        return bookRepository.save(Book.builder()
                .title(dto.getTitle())
                .genre(genre)
                .authors(Set.of(author))
                .build()
        );
    }

    @Transactional
    @Override
    public boolean update(BookDto dto) {
        return bookRepository.findById(dto.getId())
                .map(book -> {
                    book.setTitle(dto.getTitle());
                    return bookRepository.save(book).isPresent();
                }).orElse(false);
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return bookRepository.deleteById(id);
    }

}
