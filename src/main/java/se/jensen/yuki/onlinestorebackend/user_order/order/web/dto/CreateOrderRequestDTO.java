package se.jensen.yuki.onlinestorebackend.user_order.order.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record CreateOrderRequestDTO(List<OrderItemDTO> items, ShippingInfoDTO shippingInfo, String status) {
}
