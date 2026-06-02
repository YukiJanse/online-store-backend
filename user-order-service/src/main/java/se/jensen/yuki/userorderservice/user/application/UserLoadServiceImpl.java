package se.jensen.yuki.userorderservice.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.userorderservice.shared.exception.UserNotFoundException;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaEntity;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaRepository;
import se.jensen.yuki.userorderservice.user.web.dto.UserInfoDTO;
import se.jensen.yuki.userorderservice.user.web.mapper.UserResponseMapper;

@Service
@RequiredArgsConstructor
public class UserLoadServiceImpl implements UserLoadService {
    private final UserJpaRepository userJpaRepository;
    private final UserResponseMapper userResponseMapper;

    @Override
    public UserJpaEntity requireJpaById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist with id=" + id));
    }

    @Override
    public UserJpaEntity requireJpaByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist with email=" + email));
    }

    @Override
    public UserInfoDTO requireUserInfoById(Long id) {
        return userJpaRepository.findById(id)
                .map(userResponseMapper::toUserInfoDto)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist with id=" + id));
    }
}
