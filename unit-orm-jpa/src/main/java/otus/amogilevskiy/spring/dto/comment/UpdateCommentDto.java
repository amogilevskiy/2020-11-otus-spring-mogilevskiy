package otus.amogilevskiy.spring.dto.comment;

import lombok.Data;

@Data
public class UpdateCommentDto {

    private final long id;

    private final String text;

}
