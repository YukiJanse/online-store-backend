package se.jensen.yuki.userorderservice.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.userorderservice.user.domain.User;
import se.jensen.yuki.userorderservice.user.domain.UserRepository;
import se.jensen.yuki.userorderservice.user.domain.vo.*;
import se.jensen.yuki.userorderservice.user.web.dto.ChangeNamesRequestDTO;
import se.jensen.yuki.userorderservice.user.web.dto.ChangeProfileRequestDTO;
import se.jensen.yuki.userorderservice.user.web.dto.UserInfoDTO;
import se.jensen.yuki.userorderservice.user.web.mapper.UserResponseMapper;

@Service
@RequiredArgsConstructor
public class ChangeProfileUseCase {
    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;

    @Transactional
    public UserInfoDTO execute(Long userId, ChangeProfileRequestDTO requestDTO) {
        User user = userRepository.findById(UserId.of(userId));
        FirstName firstName = FirstName.of(requestDTO.firstName());
        LastName lastName = LastName.of(requestDTO.lastName());
        PhoneNumber phoneNumber = PhoneNumber.of(requestDTO.phoneNumber());
        Address address = Address.of(
                requestDTO.address().street(),
                requestDTO.address().postalCode(),
                requestDTO.address().city(),
                requestDTO.address().country()
        );
        user.changeProfile(firstName, lastName, phoneNumber, address);
        return userResponseMapper.domainToUserInfoDto(userRepository.save(user));
    }
}
