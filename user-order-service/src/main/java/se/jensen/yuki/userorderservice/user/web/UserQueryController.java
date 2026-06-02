package se.jensen.yuki.userorderservice.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.userorderservice.security.service.CurrentUserProvider;
import se.jensen.yuki.userorderservice.user.application.UserLoadService;
import se.jensen.yuki.userorderservice.user.web.dto.UserInfoDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserQueryController {
    private final UserLoadService userLoadService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getMyProfile() {
        Long myId = currentUserProvider.currentUserId();
        return ResponseEntity
                .ok()
                .body(userLoadService.requireUserInfoById(myId));
    }
}
