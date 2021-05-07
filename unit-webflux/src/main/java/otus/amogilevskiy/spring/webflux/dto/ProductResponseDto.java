package otus.amogilevskiy.spring.webflux.dto;

import lombok.Data;

@Data
public class ProductResponseDto {

    private String id;
    private String title;
    private String description;

}
