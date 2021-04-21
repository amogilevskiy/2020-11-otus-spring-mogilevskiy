package otus.amogilevskiy.spring.dto.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private final String id;
    private final String title;
    private final String genreId;
    private final String authorId;

}
