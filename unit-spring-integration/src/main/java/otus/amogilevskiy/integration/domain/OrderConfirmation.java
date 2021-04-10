package otus.amogilevskiy.integration.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class OrderConfirmation {

    private final Order order;
    private final OrderConfirmationStatus status;
    private final String comment;
    private final String trackingId;

}
