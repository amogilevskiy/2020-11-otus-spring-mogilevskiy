package otus.amogilevskiy.integration.service.order;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.integration.domain.Order;
import otus.amogilevskiy.integration.domain.OrderItem;

@Service("availability")
public class AvailabilityOrderValidationService implements OrderValidationService {

    @Override
    public Order validate(Order order) {
        var items = order.getItems();
        if (items.isEmpty()) {
            throw new OrderValidationException("Your order is empty.");
        } else {
            for (var item : items) {
                if (!isAvailable(item)) {
                    throw new OrderValidationException(String.format("%s is not available at the moment.", item.getName()));
                }
            }
        }
        return order;
    }

    private boolean isAvailable(OrderItem item) {
        return !item.getName().equalsIgnoreCase("Milk");
    }

}
