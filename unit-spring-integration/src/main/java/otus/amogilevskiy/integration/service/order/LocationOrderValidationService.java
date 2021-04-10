package otus.amogilevskiy.integration.service.order;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.integration.domain.Order;

@Service("location")
public class LocationOrderValidationService implements OrderValidationService {

    private static final String AVAILABLE_DELIVERY_ZONE = "Russia";

    @Override
    public Order validate(Order order) {
        if (AVAILABLE_DELIVERY_ZONE.equalsIgnoreCase(order.getAddress().getCountry())) {
            return order;
        } else {
            throw new OrderValidationException("Delivery is not available in your region.");
        }
    }
}
