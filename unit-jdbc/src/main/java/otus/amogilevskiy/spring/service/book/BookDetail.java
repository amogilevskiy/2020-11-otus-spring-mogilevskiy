package otus.amogilevskiy.spring.service.book;

import lombok.Data;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.domain.Genre;

@Data
public class BookDetail {

    private final long id;

    private final String title;

    private final Author author;

    private final Genre genre;

}
