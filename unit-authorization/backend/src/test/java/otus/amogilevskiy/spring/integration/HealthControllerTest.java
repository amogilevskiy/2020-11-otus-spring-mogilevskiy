package otus.amogilevskiy.spring.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerTest extends DatabaseContainerBaseTest {

    @LocalManagementPort
    private long localManagementPort;

    @Autowired
    private TestRestTemplate client;

    @Test
    void shouldReturnOk() {
        var url = String.format("http://localhost:%d/actuator/health", localManagementPort);
        var response = client.getForEntity(url, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
