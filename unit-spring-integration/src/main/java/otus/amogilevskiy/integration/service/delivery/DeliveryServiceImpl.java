package otus.amogilevskiy.integration.service.delivery;

import org.springframework.stereotype.Service;
import otus.amogilevskiy.integration.domain.Delivery;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public void send(Delivery delivery) {
        try {
            Thread.sleep(5000);
            System.out.printf("Delivered order with tracking id: %s%n", delivery.getTrackingId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
