package se.jensen.yuki.onlinestorebackend.user_order.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.onlinestorebackend.security.service.JwtService;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Address;
import se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure.UserJpaEntity;
import se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure.UserJpaRepository;
import se.jensen.yuki.onlinestorebackend.user_order.user.web.dto.AuthRegisterRequestDTO;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public TokenPair execute(AuthRegisterRequestDTO dto) {
        UserJpaEntity user = UserJpaEntity.builder()
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .phoneNumber(dto.phoneNumber())
                .address(Address.of(dto.street(), dto.postalCode(), dto.city(), dto.country()))
                .role("USER")
                .build();

        UserJpaEntity registeredUser = userJpaRepository.save(user);
        String access = jwtService.generateAccessToken(registeredUser.getId());
        String refresh = refreshTokenService.createRefreshToken(registeredUser);

        return new TokenPair(access, refresh);
    }
}
