package otus.amogilevskiy.integration.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class Order {

    private final Address address;
    private final List<OrderItem> items;

}
