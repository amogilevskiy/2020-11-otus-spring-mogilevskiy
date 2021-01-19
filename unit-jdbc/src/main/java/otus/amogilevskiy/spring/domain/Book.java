package otus.amogilevskiy.spring.domain;

import lombok.Data;

@Data
public class Book {

    private final long id;

    private final String title;

    private final Long authorId;

    private final Long genreId;

}
