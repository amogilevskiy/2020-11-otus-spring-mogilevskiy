package otus.amogilevskiy.integration.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Address {

    private final String zip;
    private final String country;
    private final String city;
    private final String street;
    private final String addressText;
    private final String phone;

}
