package se.jensen.yuki.userorderservice.order.web.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemDTO(Long productId, String title, BigDecimal price, String image, int quantity) {
}
