package se.jensen.yuki.userorderservice.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.userorderservice.user.domain.User;
import se.jensen.yuki.userorderservice.user.domain.UserRepository;
import se.jensen.yuki.userorderservice.user.domain.vo.FirstName;
import se.jensen.yuki.userorderservice.user.domain.vo.LastName;
import se.jensen.yuki.userorderservice.user.domain.vo.UserId;
import se.jensen.yuki.userorderservice.user.web.dto.ChangeNamesRequestDTO;
import se.jensen.yuki.userorderservice.user.web.dto.UserInfoDTO;
import se.jensen.yuki.userorderservice.user.web.mapper.UserResponseMapper;

@Service
@RequiredArgsConstructor
public class ChangeNamesUseCase {
    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;

    @Transactional
    public UserInfoDTO execute(Long userId, ChangeNamesRequestDTO requestDTO) {
        User user = userRepository.findById(UserId.of(userId));
        FirstName firstName = FirstName.of(requestDTO.firstName());
        LastName lastName = LastName.of(requestDTO.lastName());
        user.changeNames(firstName, lastName);
        return userResponseMapper.domainToUserInfoDto(userRepository.save(user));
    }
}
