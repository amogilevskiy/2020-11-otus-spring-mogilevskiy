package otus.amogilevskiy.integration;

import org.junit.jupiter.api.Test;
import otus.amogilevskiy.integration.domain.Address;
import otus.amogilevskiy.integration.domain.Order;
import otus.amogilevskiy.integration.domain.OrderItem;
import otus.amogilevskiy.integration.service.order.AvailabilityOrderValidationService;
import otus.amogilevskiy.integration.service.order.OrderValidationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AvailabilityOrderValidationServiceTest {

    @Test
    void shouldRaiseExceptionWhenOrderIsEmpty() {
        var validationService = new AvailabilityOrderValidationService();
        var order = new Order(Address.builder().build(), List.of());

        var e = assertThrows(OrderValidationException.class, () -> {
            validationService.validate(order);
        });

        assertThat(e.getMessage()).isEqualTo("Your order is empty.");
    }

    @Test
    void shouldRaiseExceptionWhenItemIsNotAvailable() {
        var validationService = new AvailabilityOrderValidationService();
        var absentItem = new OrderItem("Milk");
        var order = new Order(Address.builder().build(), List.of(absentItem));

        var e = assertThrows(OrderValidationException.class, () -> {
            validationService.validate(order);
        });

        assertThat(e.getMessage()).isEqualTo("Milk is not available at the moment.");
    }

    @Test
    void shouldReturnOrderWhenAllItemsAreAvailable() {
        var validationService = new AvailabilityOrderValidationService();
        var order = new Order(
                Address.builder().build(),
                List.of(
                        new OrderItem("Sugar"),
                        new OrderItem("Salt")
                )
        );

        var actualOrder = validationService.validate(order);

        assertThat(actualOrder).isEqualTo(order);
    }

}
