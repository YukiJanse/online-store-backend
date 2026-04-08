package se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
