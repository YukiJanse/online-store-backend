package se.jensen.yuki.userorderservice.order.web.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
public record OrderDetailDTO(Long id, List<OrderItemDTO> items, ShippingInfoDTO shippingInfo, Instant orderedAt, BigDecimal totalAmount, String status) {
}
