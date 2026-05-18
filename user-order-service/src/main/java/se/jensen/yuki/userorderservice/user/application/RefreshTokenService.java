package se.jensen.yuki.userorderservice.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.userorderservice.security.service.JwtService;
import se.jensen.yuki.userorderservice.user.domain.RefreshToken;
import se.jensen.yuki.userorderservice.user.infrastructure.RefreshTokenRepository;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaEntity;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final long REFRESH_TOKEN_DURATION_SECONDS = 60L * 60 * 24 * 30; // 30 days


    public String createRefreshToken(UserJpaEntity user) {
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(generateTokenValue())
                .expiresAt(Instant.now().plusSeconds(REFRESH_TOKEN_DURATION_SECONDS))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(rt).getToken();
    }

    public Optional<TokenPair> refreshAccessToken(String refreshTokenValue) {
        Optional<RefreshToken> found = refreshTokenRepository.findByToken(refreshTokenValue);

        if (found.isEmpty()) {
            return Optional.empty();
        }

        RefreshToken rt = found.get();
        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) {
            // revoke/clean up the token
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
            return Optional.empty();
        }

        UserJpaEntity user = rt.getUser();
        // rotation: revoke old token and create a new one
        rt.setRevoked(true);
        refreshTokenRepository.save(rt);

        String newRefreshToken = createRefreshToken(user);
        String newAccessToken = jwtService.generateAccessToken(user.getId(), user.getRole());

        return Optional.of(new TokenPair(newAccessToken, newRefreshToken));
    }

    private String generateTokenValue() {
        return UUID.randomUUID() + "-" + UUID.randomUUID();
    }

    public void revokeAllForUser(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

}
