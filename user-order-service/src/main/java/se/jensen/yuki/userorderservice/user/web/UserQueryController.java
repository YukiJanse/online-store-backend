package se.jensen.yuki.userorderservice.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.userorderservice.user.application.UserLoadService;

@RestController
@RequiredArgsConstructor
public class UserQueryController {
    private final UserLoadService userLoadService;
}
