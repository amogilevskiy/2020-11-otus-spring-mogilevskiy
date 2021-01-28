package otus.amogilevskiy.spring.facade.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.facade.author.AuthorFacade;
import otus.amogilevskiy.spring.facade.book.BookFacade;
import otus.amogilevskiy.spring.facade.genre.GenreFacade;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class LibraryFacadeImpl implements LibraryFacade {

    private final BookFacade bookFacade;
    private final AuthorFacade authorFacade;
    private final GenreFacade genreFacade;
    private final LocalizationService localizationService;

    @Override
    public String findBookById(long id) {
        return bookFacade.findBookById(id);
    }

    @Override
    public String findAllBooks() {
        return bookFacade.findAllBooks();
    }

    @Override
    public String addBook() {
        return authorFacade.findOrCreateAuthor()
                .map(author -> genreFacade.findOrCreateGenre()
                        .map(genre -> bookFacade.create(author, genre))
                        .orElse(localizationService.localize("error.genreNotAdded")))
                .orElse(localizationService.localize("error.authorNotAdded"));
    }

    @Override
    public String updateBookById(long id) {
        return bookFacade.updateBookById(id);
    }

    @Override
    public String deleteBookById(long id) {
        return bookFacade.deleteBookById(id);
    }

}
