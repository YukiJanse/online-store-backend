package se.jensen.yuki.userorderservice.user.web.dto;

import se.jensen.yuki.userorderservice.user.domain.vo.Address;

public record UserInfoDTO(
        String username,
        String email,
        String phoneNumber,
        Address address) {
}
