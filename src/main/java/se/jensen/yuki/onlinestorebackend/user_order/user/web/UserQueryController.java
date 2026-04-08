package se.jensen.yuki.onlinestorebackend.user_order.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.UserLoadService;

@RestController
@RequiredArgsConstructor
public class UserQueryController {
    private final UserLoadService userLoadService;
}
