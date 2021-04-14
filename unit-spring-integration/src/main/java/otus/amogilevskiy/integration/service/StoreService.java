package otus.amogilevskiy.integration.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import otus.amogilevskiy.integration.domain.Order;
import otus.amogilevskiy.integration.domain.OrderConfirmation;

@MessagingGateway(errorChannel = "errorsChannel")
public interface StoreService {

    @Gateway(requestChannel = "ordersChannel", replyChannel = "orderConfirmationsChannel")
    OrderConfirmation placeOrder(Order order);


}
