package otus.amogilevskiy.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import otus.amogilevskiy.integration.domain.Address;
import otus.amogilevskiy.integration.domain.Order;
import otus.amogilevskiy.integration.domain.OrderConfirmationStatus;
import otus.amogilevskiy.integration.domain.OrderItem;
import otus.amogilevskiy.integration.service.StoreService;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);

        var storeService = context.getBean(StoreService.class);

        for (var i = 0; i < 10; i++) {
            var availableCountry = i % 2 == 1;
            var country = availableCountry ? "Russia" : "Middle-earth";

            var availableItems = i % 3 != 0;
            var items = createItems(availableItems);

            var address = new Address("any zip", country, "any city", "any street", "", "");
            var order = Order.builder()
                    .address(address)
                    .items(items)
                    .build();
            var confirmation = storeService.placeOrder(order);

            if (confirmation.getStatus() == OrderConfirmationStatus.REJECTED) {
                System.out.printf("Order rejected: %s%n", confirmation.getComment());
            } else {
                System.out.printf("Order confirmed. Tracking id: %s%n", confirmation.getTrackingId());
            }
        }
    }

    private static List<OrderItem> createItems(boolean available) {
        return available ? List.of(new OrderItem("Sugar")) : List.of(new OrderItem("Milk"));
    }

}
