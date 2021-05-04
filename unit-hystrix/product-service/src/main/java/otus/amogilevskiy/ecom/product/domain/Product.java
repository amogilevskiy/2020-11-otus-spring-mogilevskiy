package otus.amogilevskiy.ecom.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(precision = 11, scale = 2)
    private BigDecimal basePrice;

    @Column(precision = 11, scale = 2)
    private BigDecimal currentPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
