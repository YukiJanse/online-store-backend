package se.jensen.yuki.onlinestorebackend.user_order.order.web.dto;

import lombok.Builder;

@Builder
public record ShippingInfoDTO(String name, AddressDTO address) {
}
