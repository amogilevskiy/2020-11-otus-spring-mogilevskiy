package otus.amogilevskiy.spring.facade.library;

public interface LibraryFacade {

    String findBookById(long id);

    String findAllBooks();

    String addBook();

    String updateBookById(long id);

    String deleteBookById(long id);

    String deleteCommentById(long id);

    String showAllCommentsByBookId(long bookId);

    String addComment(long bookId, String text);

    String updateCommentById(long id, String text);

}
