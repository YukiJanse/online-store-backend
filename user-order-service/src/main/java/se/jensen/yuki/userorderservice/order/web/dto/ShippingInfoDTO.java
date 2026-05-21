package se.jensen.yuki.userorderservice.order.web.dto;

import lombok.Builder;

@Builder
public record ShippingInfoDTO(String name, AddressDTO address) {
}
