package se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure;

import org.springframework.stereotype.Component;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.User;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.*;

@Component
public class UserJpaMapper {
    public void toEntity(User user, UserJpaEntity entity) {
        entity.setId(user.getId().getValue());
        entity.setUsername(user.getUsername().getValue());
        entity.setEmail(user.getEmail().getValue());
        entity.setPassword(user.getPassword().getValue());
        entity.setAddress(user.getAddress());
        entity.setPhoneNumber(user.getPhoneNumber().getValue());
        entity.setRole("USER");
    }

    public User toDomain(UserJpaEntity entity) {
        return User.reconstruct(
                UserId.of(entity.getId()),
                Username.of(entity.getUsername()),
                Email.of(entity.getEmail()),
                PhoneNumber.of(entity.getPhoneNumber()),
                HashedPassword.of(entity.getPassword()),
                Address.of(entity.getAddress().getStreet(),
                        entity.getAddress().getPostalCode(),
                        entity.getAddress().getCity(),
                        entity.getAddress().getCountry()
                        )
        );
    }
}
