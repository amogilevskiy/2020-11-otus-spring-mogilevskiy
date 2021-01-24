package otus.amogilevskiy.spring.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Genre {

    private final Long id;

    private final String title;

}
