package otus.amogilevskiy.spring.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String title;
    private Long genreId;
    private Set<Long> authorIds;

}
