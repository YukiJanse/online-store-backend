package se.jensen.yuki.userorderservice.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.userorderservice.security.service.JwtService;
import se.jensen.yuki.userorderservice.user.domain.User;
import se.jensen.yuki.userorderservice.user.domain.vo.*;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaEntity;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaMapper;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaRepository;
import se.jensen.yuki.userorderservice.user.web.dto.AuthRegisterRequestDTO;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserJpaMapper userJpaMapper;

    @Transactional
    public TokenPair execute(AuthRegisterRequestDTO dto) {
        User userDomain = User.create(Username.of(dto.username()),
                FirstName.of(dto.username()),
                LastName.of(dto.username()),
                Email.of(dto.email()),
                PhoneNumber.of(dto.phoneNumber()),
                HashedPassword.of(passwordEncoder.encode(dto.password())),
                Address.of("street", "11111", "city", "Sweden")
                );

        UserJpaEntity userJpaEntity = new UserJpaEntity();

        userJpaMapper.toEntity(userDomain, userJpaEntity);

//        UserJpaEntity userJpaEntity = UserJpaEntity.builder()
//                .username(dto.username())
//                .email(dto.email())
//                .password(passwordEncoder.encode(dto.password()))
//                .phoneNumber(dto.phoneNumber())
//                .address(Address.of("street", "11111", "city", "Sweden"))
//                .role("USER")
//                .build();

        UserJpaEntity registeredUser = userJpaRepository.save(userJpaEntity);
        String access = jwtService.generateAccessToken(registeredUser.getId(), registeredUser.getRole());
        String refresh = refreshTokenService.createRefreshToken(registeredUser);

        return new TokenPair(access, refresh);
    }
}
