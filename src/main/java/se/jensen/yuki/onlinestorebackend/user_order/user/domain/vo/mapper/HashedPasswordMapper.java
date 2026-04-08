package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.HashedPassword;

@Mapper(componentModel = "spring")
public interface HashedPasswordMapper {
    /**
     * Maps a HashedPassword value object to its String representation.
     *
     * @param password the HashedPassword to map
     * @return the String representation of the HashedPassword, or null if the input is null
     */
    default String map(HashedPassword password) {
        return password == null ? null : password.getValue();
    }

    /**
     * Maps a String to a HashedPassword value object.
     *
     * @param value the String to map
     * @return the HashedPassword value object, or null if the input is null
     */
    default HashedPassword map(String value) {
        return value == null ? null : HashedPassword.of(value);
    }
}
