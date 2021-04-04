package otus.amogilevskiy.spring.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponseDto {

    private final String code;
    private final String message;

}
