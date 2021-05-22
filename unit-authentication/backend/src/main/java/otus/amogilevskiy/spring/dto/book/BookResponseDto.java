package otus.amogilevskiy.spring.dto.book;

import lombok.Data;
import otus.amogilevskiy.spring.dto.author.AuthorResponseDto;
import otus.amogilevskiy.spring.dto.genre.GenreResponseDto;

import java.util.Set;

@Data
public class BookResponseDto {

    private long id;
    private String title;
    private GenreResponseDto genre;
    private Set<AuthorResponseDto> authors;
}
