package otus.amogilevskiy.ecom.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductItemResponseDto {

    public static int UNKNOWN_QUANTITY = -1;

    private long id;
    private int quantity;

}
