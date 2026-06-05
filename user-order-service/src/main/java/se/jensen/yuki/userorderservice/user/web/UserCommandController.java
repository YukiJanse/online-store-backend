package se.jensen.yuki.userorderservice.user.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.userorderservice.security.service.CurrentUserProvider;
import se.jensen.yuki.userorderservice.user.application.*;
import se.jensen.yuki.userorderservice.user.web.dto.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserCommandController {
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final RefreshTokenService refreshTokenService;
    private final ChangeNamesUseCase changeNamesUseCase;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@RequestBody AuthRegisterRequestDTO requestDTO,
                                          HttpServletResponse response) {
        TokenPair tokenPair = registerUserUseCase.execute(requestDTO);

        setRefreshTokenCookie(response, tokenPair.refreshToken());

        return ResponseEntity.ok(new AuthResponseDTO(tokenPair.accessToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO requestDTO,
                                                 HttpServletResponse response) {
        TokenPair tokenPair = loginUserUseCase.execute(requestDTO);

        setRefreshTokenCookie(response, tokenPair.refreshToken());

        return ResponseEntity.ok(new AuthResponseDTO(tokenPair.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                     HttpServletResponse response) {
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
        return ResponseEntity
                .ok()
                .body(changeNamesUseCase.execute(currentUserProvider.currentUserId(), requestDTO));
    }
}
