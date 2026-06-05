package se.jensen.yuki.userorderservice.order.web.dto;

import lombok.Builder;

@Builder
public record ShippingInfoDTO(String firstName, String lastName, AddressDTO address) {
}
