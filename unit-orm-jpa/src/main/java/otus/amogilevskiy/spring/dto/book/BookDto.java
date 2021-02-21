package otus.amogilevskiy.spring.dto.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private final Long id;
    private final String title;
    private final Long genreId;
    private final Long authorId;

}
