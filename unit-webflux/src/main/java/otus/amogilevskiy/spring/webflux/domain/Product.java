package otus.amogilevskiy.spring.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@Builder
@Document(collection = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    private String title;

    private String description;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal basePrice;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal currentPrice;

    private Category category;

}