package se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.jensen.yuki.onlinestorebackend.shared.exception.UserNotFoundException;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.User;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.UserRepository;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Email;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.UserId;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;

    @Override
    public User findById(UserId id) {
        return userJpaRepository.findById(id.getValue())
                .map(userJpaMapper::toDomain)
                .orElseThrow(() -> new UserNotFoundException("user does not exist with id=" + id));

    }

    @Override
    public void save(User user) {
        UserJpaEntity entity;
        if (user.getId() == null) {
            entity = new UserJpaEntity();
            userJpaMapper.toEntity(user, entity);
        } else {
            entity = userJpaRepository.findById(user.getId().getValue())
                    .orElseThrow(() -> new UserNotFoundException("Invalid User ID=" + user.getId()));
            userJpaMapper.toEntity(user, entity);
        }
        userJpaRepository.save(entity);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userJpaRepository.existsByEmail(email.toString());
    }

    @Override
    public void deleteById(UserId id) {
        userJpaRepository.deleteById(id.getValue());
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(userJpaMapper::toDomain)
                .toList();
    }
}
