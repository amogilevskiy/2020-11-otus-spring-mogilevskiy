package otus.amogilevskiy.spring.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import otus.amogilevskiy.spring.repository.BookRepository;

@RequiredArgsConstructor
@Component
public class LibraryHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        return bookRepository.count() > 0 ? Health.up().build() : Health.down()
                .status(Status.UNKNOWN)
                .withDetail("message", "The library is empty.")
                .build();
    }

}
