package otus.amogilevskiy.spring.dto.author;

import lombok.Data;

@Data
public class CreateAuthorDto {

    private final String firstName;

    private final String lastName;

    private final String middleName;

}
