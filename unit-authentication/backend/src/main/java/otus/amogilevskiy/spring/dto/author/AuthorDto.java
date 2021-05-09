package otus.amogilevskiy.spring.dto.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String firstName;
    private String lastName;
    private String middleName;

}
