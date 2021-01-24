package otus.amogilevskiy.spring.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Author {

    private final Long id;

    private final String firstName;

    private final String lastName;

    private final String middleName;

}
