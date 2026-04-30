package se.jensen.yuki.onlinestorebackend.user_order.order.web.dto;

import lombok.Builder;

@Builder
public record AddressDTO(String street, String postalCode, String city, String country) {
}
