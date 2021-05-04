package otus.amogilevskiy.ecom.inventory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "product_items")
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {

    @Id
    private Long id;

    private int quantity;

}
