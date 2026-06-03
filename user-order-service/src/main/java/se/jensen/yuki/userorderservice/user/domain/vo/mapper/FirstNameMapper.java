package se.jensen.yuki.userorderservice.user.domain.vo.mapper;

import se.jensen.yuki.userorderservice.user.domain.vo.FirstName;

/**
 * MapStruct mapper for converting between FirstName value object and String.
 */
public interface FirstNameMapper {
    /**
     * Maps a firstName value object to its String representation.
     *
     * @param firstName the firstName to map
     * @return the String representation of the firstName, or null if the input is null
     */
    default String map(FirstName firstName) {
        return firstName == null ? null : firstName.getValue();
    }

    /**
     * Maps a String to a firstName value object.
     *
     * @param firstName the String to map
     * @return the FirstName value object, or null if the input is null
     */
    default FirstName map(String firstName) {
        return firstName == null ? null : FirstName.of(firstName);
    }
}
