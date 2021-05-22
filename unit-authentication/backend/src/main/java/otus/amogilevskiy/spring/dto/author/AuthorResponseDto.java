package otus.amogilevskiy.spring.dto.author;

import lombok.Data;

@Data
public class AuthorResponseDto {

    private long id;
    private String firstName;
    private String lastName;
    private String middleName;

}
