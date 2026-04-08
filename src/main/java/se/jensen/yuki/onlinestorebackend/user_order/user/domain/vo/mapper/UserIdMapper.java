package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.UserId;

/**
 * MapStruct mapper for converting between UserId value object and Long.
 */
@Mapper(componentModel = "spring")
public interface UserIdMapper {
    /**
     * Maps a UserId value object to its Long representation.
     *
     * @param userId the UserId to map
     * @return the Long representation of the UserId, or null if the input is null
     */
    default Long map(UserId userId) {
        return userId == null ? null : userId.getValue();
    }

    /**
     * Maps a Long to a UserId value object.
     *
     * @param value the Long to map
     * @return the UserId value object, or null if the input is null
     */
    default UserId map(Long value) {
        return value == null ? null : UserId.of(value);
    }
}
