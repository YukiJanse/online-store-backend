package se.jensen.yuki.onlinestorebackend.user_order.user.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.LoginUserUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.RefreshTokenService;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.RegisterUserUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.TokenPair;
import se.jensen.yuki.onlinestorebackend.user_order.user.web.dto.AuthRegisterRequestDTO;
import se.jensen.yuki.onlinestorebackend.user_order.user.web.dto.AuthResponseDTO;
import se.jensen.yuki.onlinestorebackend.user_order.user.web.dto.LoginDTO;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserCommandController {
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final RefreshTokenService refreshTokenService;

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
                .secure(false) // TODO: set to true in production
                .path("/")
                .maxAge(60L * 60 * 24 * 30)
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
