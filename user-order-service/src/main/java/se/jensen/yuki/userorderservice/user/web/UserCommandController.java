package se.jensen.yuki.userorderservice.user.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.userorderservice.security.service.CurrentUserProvider;
import se.jensen.yuki.userorderservice.user.application.*;
import se.jensen.yuki.userorderservice.user.web.dto.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserCommandController {
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final RefreshTokenService refreshTokenService;
    private final ChangeNamesUseCase changeNamesUseCase;
    private final CurrentUserProvider currentUserProvider;
    private final ChangeProfileUseCase changeProfileUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody AuthRegisterRequestDTO requestDTO,
                                          HttpServletResponse response) {
        log.info("starting to register user");
        TokenPair tokenPair = registerUserUseCase.execute(requestDTO);

        setRefreshTokenCookie(response, tokenPair.refreshToken());

        return ResponseEntity.ok(new AuthResponseDTO(tokenPair.accessToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO requestDTO,
                                                 HttpServletResponse response) {
        log.info("starting to login");
        TokenPair tokenPair = loginUserUseCase.execute(requestDTO);

        setRefreshTokenCookie(response, tokenPair.refreshToken());

        return ResponseEntity.ok(new AuthResponseDTO(tokenPair.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                     HttpServletResponse response) {
        log.info("starting to create refreshToken");
        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }

        // refresh access token
        Optional<TokenPair> maybe = refreshTokenService.refreshAccessToken(refreshToken);
        // invalid refresh token
        if (maybe.isEmpty()) {
            // clear cookie
            ResponseCookie clear = ResponseCookie.from("refreshToken", "")
                    .path("/")
                    .httpOnly(true)
                    .maxAge(0)
                    .build();
            response.addHeader("Set-Cookie", clear.toString());
            return ResponseEntity.status(401).build();
        }

        TokenPair tokenPair = maybe.get();
        // set new refresh token cookie
        setRefreshTokenCookie(response, tokenPair.refreshToken());

        return ResponseEntity.ok(new AuthResponseDTO(tokenPair.accessToken()));
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        log.info("Setting refresh token cookie");
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60L * 60 * 24 * 30)
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    @PutMapping("/names")
    public ResponseEntity<UserInfoDTO> changeNames(@RequestBody ChangeNamesRequestDTO requestDTO) {
        log.info("starting to change names");
        return ResponseEntity
                .ok()
                .body(changeNamesUseCase.execute(currentUserProvider.currentUserId(), requestDTO));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserInfoDTO> changeProfile(@RequestBody ChangeProfileRequestDTO requestDTO) {
        log.info("starting to change names");
        return ResponseEntity
                .ok()
                .body(changeProfileUseCase.execute(currentUserProvider.currentUserId(), requestDTO));
    }
}
