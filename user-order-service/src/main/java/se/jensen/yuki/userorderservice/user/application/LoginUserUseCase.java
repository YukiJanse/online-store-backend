package se.jensen.yuki.userorderservice.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import se.jensen.yuki.userorderservice.security.infrastructure.CustomUserDetails;
import se.jensen.yuki.userorderservice.security.service.JwtService;
import se.jensen.yuki.userorderservice.shared.exception.UserNotFoundException;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaEntity;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaRepository;
import se.jensen.yuki.userorderservice.user.web.dto.LoginDTO;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {
    private final JwtService jwtService;
    private final UserJpaRepository userJpaRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public TokenPair execute(LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserJpaEntity user = userJpaRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UserNotFoundException("No user found"));

        String access = jwtService.generateAccessToken(userDetails.getId(), user.getRole());
        String refresh = refreshTokenService.createRefreshToken(user);

        return new TokenPair(access, refresh);
    }
}
