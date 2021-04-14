package otus.amogilevskiy.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.integration.domain.Address;
import otus.amogilevskiy.integration.domain.Order;
import otus.amogilevskiy.integration.domain.OrderConfirmationStatus;
import otus.amogilevskiy.integration.service.StoreService;
import otus.amogilevskiy.integration.service.delivery.DeliveryService;
import otus.amogilevskiy.integration.service.order.OrderValidationException;
import otus.amogilevskiy.integration.service.order.OrderValidationService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @MockBean(name = "location")
    private OrderValidationService locationValidationService;

    @MockBean(name = "availability")
    private OrderValidationService availabilityValidationService;

    @MockBean
    private DeliveryService deliveryService;

    @Test
    void shouldReturnRejectedOrderWhenLocationValidationFails() {
        var order = createOrder();
        var reason = "Delivery is not available in your region.";
        when(locationValidationService.validate(order)).thenThrow(new OrderValidationException(reason));
        when(availabilityValidationService.validate(order)).thenReturn(order);

        var actualConfirmation = storeService.placeOrder(order);

        assertThat(actualConfirmation.getStatus()).isEqualTo(OrderConfirmationStatus.REJECTED);
        assertThat(actualConfirmation.getComment()).isEqualTo(reason);
    }

    @Test
    void shouldReturnRejectedOrderWhenAvailabilityValidationFails() {
        var order = createOrder();
        var reason = "Item is not available at the moment.";
        when(locationValidationService.validate(order)).thenReturn(order);
        when(availabilityValidationService.validate(order)).thenThrow(new OrderValidationException(reason));

        var actualConfirmation = storeService.placeOrder(order);

        assertThat(actualConfirmation.getStatus()).isEqualTo(OrderConfirmationStatus.REJECTED);
        assertThat(actualConfirmation.getComment()).isEqualTo(reason);
    }

    @Test
    void shouldReturnConfirmedOrderWhenFullValidationPasses() {
        var order = createOrder();
        when(locationValidationService.validate(order)).thenReturn(order);
        when(availabilityValidationService.validate(order)).thenReturn(order);

        var actualConfirmation = storeService.placeOrder(order);
        assertThat(actualConfirmation.getStatus()).isEqualTo(OrderConfirmationStatus.CONFIRMED);
    }

    @Test
    void shouldSendDeliveryWhenOrderIsValid() {
        var order = createOrder();
        when(locationValidationService.validate(order)).thenReturn(order);
        when(availabilityValidationService.validate(order)).thenReturn(order);

        storeService.placeOrder(order);
        verify(deliveryService).send(any());
    }

    private Order createOrder() {
        return Order.builder()
                .address(Address.builder().build())
                .items(List.of())
                .build();
    }

}
