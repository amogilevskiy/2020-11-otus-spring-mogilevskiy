package otus.amogilevskiy.spring.webflux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String id;

    private String title;

    public Category(String title) {
        this.title = title;
    }

}
