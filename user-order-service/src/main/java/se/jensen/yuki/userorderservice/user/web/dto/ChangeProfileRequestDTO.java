package se.jensen.yuki.userorderservice.user.web.dto;

import se.jensen.yuki.userorderservice.order.web.dto.AddressDTO;

public record ChangeProfileRequestDTO(
        String firstName,
        String lastName,
        String phoneNumber,
        AddressDTO address
        ) {
}
