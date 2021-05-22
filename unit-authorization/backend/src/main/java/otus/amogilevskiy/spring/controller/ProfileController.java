package otus.amogilevskiy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otus.amogilevskiy.spring.domain.User;
import otus.amogilevskiy.spring.dto.ProfileResponseDto;

@RequiredArgsConstructor
@RequestMapping("/api/1.0/profile")
@RestController
public class ProfileController {

    @GetMapping
    public ProfileResponseDto getUser(@AuthenticationPrincipal User user) {
        return new ProfileResponseDto(user);
    }

}
