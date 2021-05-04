package otus.amogilevskiy.ecom.product.dto;

import lombok.Data;

@Data
public class ProductResponseDto {

    private long id;
    private String title;
    private String description;
    private CategoryResponseDto category;
    private String quantity;

}
