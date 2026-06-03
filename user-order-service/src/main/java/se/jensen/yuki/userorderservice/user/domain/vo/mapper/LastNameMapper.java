package se.jensen.yuki.userorderservice.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.userorderservice.user.domain.vo.LastName;

/**
 * MapStruct mapper for converting between LastName value object and String.
 */
@Mapper(componentModel = "spring")
public interface LastNameMapper {
    /**
     * Maps a LastName value object to its String representation.
     *
     * @param lastName the LastName to map
     * @return the String representation of the LastName, or null if the input is null
     */
    default String map(LastName lastName) {
        return lastName == null ? null : lastName.getValue();
    }

    /**
     * Maps a String to a lastName value object.
     *
     * @param lastName the String to map
     * @return the LastName value object, or null if the input is null
     */
    default LastName map(String lastName) {
        return lastName == null ? null : LastName.of(lastName);
    }
}
