package otus.amogilevskiy.integration;

import org.junit.jupiter.api.Test;
import otus.amogilevskiy.integration.domain.Address;
import otus.amogilevskiy.integration.domain.Order;
import otus.amogilevskiy.integration.domain.OrderItem;
import otus.amogilevskiy.integration.service.order.LocationOrderValidationService;
import otus.amogilevskiy.integration.service.order.OrderValidationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationOrderValidationServiceTest {

    @Test
    void shouldRaiseExceptionWhenRegionIsNotSupported() {
        var validationService = new LocationOrderValidationService();
        var order = new Order(
                Address.builder()
                        .country("Middle-earth")
                        .build(),
                List.of(new OrderItem("Sugar"))
        );

        var e = assertThrows(OrderValidationException.class, () -> {
            validationService.validate(order);
        });

        assertThat(e.getMessage()).isEqualTo("Delivery is not available in your region.");
    }

    @Test
    void shouldReturnOrderWhenRegionIsSupported() {
        var validationService = new LocationOrderValidationService();
        var order = new Order(
                Address.builder()
                        .country("Russia")
                        .build(),
                List.of(new OrderItem("Sugar"))
        );

        var actualOrder = validationService.validate(order);

        assertThat(actualOrder).isEqualTo(order);
    }


}
