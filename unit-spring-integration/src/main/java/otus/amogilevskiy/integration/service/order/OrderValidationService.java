package otus.amogilevskiy.integration.service.order;

import otus.amogilevskiy.integration.domain.Order;

public interface OrderValidationService {

    Order validate(Order order);

}
