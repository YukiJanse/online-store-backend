package se.jensen.yuki.onlinestorebackend.user_order.user.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.mapper.*;

@Mapper(componentModel = "spring",
        uses = {EmailMapper.class, HashedPasswordMapper.class, PhoneNumberMapper.class, UserIdMapper.class, UsernameMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface UserResponseMapper {

}
