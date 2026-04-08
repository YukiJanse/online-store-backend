package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Email;

/**
 * MapStruct mapper for converting between Email value object and String.
 */
@Mapper(componentModel = "spring")
public interface EmailMapper {
    /**
     * Maps an Email value object to its String representation.
     *
     * @param email the Email to map
     * @return the String representation of the Email, or null if the input is null
     */
    default String map(Email email) {
        return email == null ? null : email.getValue();
    }

    /**
     * Maps a String to an Email value object.
     *
     * @param value the String to map
     * @return the Email value object, or null if the input is null
     */
    default Email map(String value) {
        return value == null ? null : Email.of(value);
    }
}
