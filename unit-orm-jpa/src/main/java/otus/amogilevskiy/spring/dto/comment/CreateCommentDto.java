package otus.amogilevskiy.spring.dto.comment;

import lombok.Data;

@Data
public class CreateCommentDto {

    private final Long bookId;

    private final String text;

}
