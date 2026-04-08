package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.PhoneNumber;

/**
 * MapStruct mapper for converting between Email value object and String.
 */
@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {
    /**
     * Maps an PhoneNumber value object to its String representation.
     *
     * @param phoneNumber the PhoneNumber to map
     * @return the String representation of the PhoneNumber, or null if the input is null
     */
    default String map(PhoneNumber phoneNumber) {
        return phoneNumber == null ? null : phoneNumber.getValue();
    }

    /**
     * Maps a String to an PhoneNumber value object.
     *
     * @param value the String to map
     * @return the PhoneNumber value object, or null if the input is null
     */
    default PhoneNumber map(String value) {
        return value == null ? null : PhoneNumber.of(value);
    }
}
