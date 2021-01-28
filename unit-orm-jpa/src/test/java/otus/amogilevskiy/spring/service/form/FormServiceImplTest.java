package otus.amogilevskiy.spring.service.form;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.amogilevskiy.spring.service.io.IOService;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FormServiceImplTest {

    @MockBean
    IOService ioService;

    @Autowired
    FormService formService;

    private static Stream<Arguments> integerInputValues() {
        return Stream.of(
                Arguments.of("1", 1),
                Arguments.of("10", 10),
                Arguments.of("NOT A NUMBER", 0)
        );
    }

    private static Stream<Arguments> stringInputValues() {
        return Stream.of(
                Arguments.of("First name", "First name"),
                Arguments.of("111", "111")
        );
    }

    @ParameterizedTest
    @MethodSource("integerInputValues")
    void shouldReturnEnteredNumber(String userValue, long expectedValue) {
        when(ioService.in()).thenReturn(userValue);

        var actualValue = formService.showIntegerFormField("Please insert your age:");

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("stringInputValues")
    void shouldReturnEnteredString(String userValue, String expectedValue) {
        when(ioService.in()).thenReturn(userValue);

        var actualValue = formService.showStringFormField("Please insert your name:");

        assertThat(actualValue).isEqualTo(expectedValue);
    }

}
