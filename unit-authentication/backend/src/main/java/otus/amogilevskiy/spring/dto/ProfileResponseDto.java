package otus.amogilevskiy.spring.dto;

import lombok.Data;
import otus.amogilevskiy.spring.domain.User;

@Data
public class LoginResponseDto {

    private final long id;
    private final String username;

    public LoginResponseDto(User user) {
        id = user.getId();
        username = user.getUsername();
    }

}
