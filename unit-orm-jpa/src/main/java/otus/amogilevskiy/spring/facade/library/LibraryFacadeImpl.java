package otus.amogilevskiy.spring.facade.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.facade.author.AuthorFacade;
import otus.amogilevskiy.spring.facade.book.BookFacade;
import otus.amogilevskiy.spring.facade.comment.CommentFacade;
import otus.amogilevskiy.spring.facade.genre.GenreFacade;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class LibraryFacadeImpl implements LibraryFacade {

    private final BookFacade bookFacade;
    private final AuthorFacade authorFacade;
    private final GenreFacade genreFacade;
    private final CommentFacade commentFacade;
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
        var authors = authorFacade.findOrCreateAuthors();
        if (authors.size() > 0) {
            return genreFacade.findOrCreateGenre()
                    .map(genre -> bookFacade.create(authors, genre))
                    .orElse(localizationService.localize("error.genreNotAdded"));
        } else {
            return localizationService.localize("error.authorNotAdded");
        }
    }

    @Override
    public String updateBookById(long id) {
        return bookFacade.updateBookById(id);
    }

    @Override
    public String deleteBookById(long id) {
        return bookFacade.deleteBookById(id);
    }

    @Override
    public String showAllCommentsByBookId(long bookId) {
        return commentFacade.findAllCommentsByBookId(bookId);
    }

    @Override
    public String addComment(long bookId, String text) {
        return commentFacade.addComment(bookId, text);
    }

    @Override
    public String deleteCommentById(long id) {
        return commentFacade.deleteCommentById(id);
    }

    @Override
    public String updateCommentById(long id, String text) {
        return commentFacade.updateCommentById(id, text);
    }
}
