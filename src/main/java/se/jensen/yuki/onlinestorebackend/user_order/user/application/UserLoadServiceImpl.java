package se.jensen.yuki.onlinestorebackend.user_order.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.onlinestorebackend.shared.exception.UserNotFoundException;
import se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure.UserJpaEntity;
import se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class UserLoadServiceImpl implements UserLoadService {
    private final UserJpaRepository userJpaRepository;

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
}
