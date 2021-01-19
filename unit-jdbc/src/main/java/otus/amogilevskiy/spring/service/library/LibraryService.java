package otus.amogilevskiy.spring.service.library;

public interface LibraryService {

    String findBookById(long id);

    String findAllBooks();

    String addBook();

    String updateBookById(long id);

    String deleteBookById(long id);

}
