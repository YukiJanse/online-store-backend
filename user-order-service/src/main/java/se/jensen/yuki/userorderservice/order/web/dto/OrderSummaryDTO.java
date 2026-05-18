package se.jensen.yuki.userorderservice.order.web.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderSummaryDTO(Long id, Instant orderedAt, BigDecimal totalAmount, String status) {
}
