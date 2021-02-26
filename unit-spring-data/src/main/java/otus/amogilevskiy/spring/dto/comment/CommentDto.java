package otus.amogilevskiy.spring.dto.comment;

import lombok.Data;

@Data
public class CommentDto {

    private final Long id;
    private final Long bookId;
    private final String text;

}
