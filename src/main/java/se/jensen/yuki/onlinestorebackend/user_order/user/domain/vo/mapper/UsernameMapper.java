package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Username;

/**
 * MapStruct mapper for converting between Username value object and String.
 */
@Mapper(componentModel = "spring")
public interface UsernameMapper {
    /**
     * Maps a Username value object to its String representation.
     *
     * @param username the Username to map
     * @return the String representation of the Username, or null if the input is null
     */
    default String map(Username username) {
        return username == null ? null : username.getValue();
    }

    /**
     * Maps a String to a Username value object.
     *
     * @param username the String to map
     * @return the Username value object, or null if the input is null
     */
    default Username map(String username) {
        return username == null ? null : Username.of(username);
    }
}
