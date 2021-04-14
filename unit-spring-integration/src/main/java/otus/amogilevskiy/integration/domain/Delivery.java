package otus.amogilevskiy.integration.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Delivery {

    private final String trackingId;
    private final Order order;

}
