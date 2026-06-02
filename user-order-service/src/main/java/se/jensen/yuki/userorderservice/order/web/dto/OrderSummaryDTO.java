package se.jensen.yuki.userorderservice.order.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderSummaryDTO(Long id, Instant orderedAt, List<OrderItemDTO> items, BigDecimal totalAmount, String status) {
}
