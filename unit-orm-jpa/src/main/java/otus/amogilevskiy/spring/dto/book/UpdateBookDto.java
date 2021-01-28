package otus.amogilevskiy.spring.dto.book;

import lombok.Data;

@Data
public class UpdateBookDto {

    private final long id;
    private final String title;

}
