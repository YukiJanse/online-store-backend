package se.jensen.yuki.userorderservice.user.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.userorderservice.user.domain.User;
import se.jensen.yuki.userorderservice.user.domain.vo.mapper.*;
import se.jensen.yuki.userorderservice.user.infrastructure.UserJpaEntity;
import se.jensen.yuki.userorderservice.user.web.dto.UserInfoDTO;

@Mapper(componentModel = "spring",
        uses = {EmailMapper.class, HashedPasswordMapper.class, PhoneNumberMapper.class, UserIdMapper.class,
                UsernameMapper.class, FirstNameMapper.class, LastNameMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface UserResponseMapper {
    UserInfoDTO toUserInfoDto(UserJpaEntity jpaEntity);
    UserInfoDTO domainToUserInfoDto(User user);
}
