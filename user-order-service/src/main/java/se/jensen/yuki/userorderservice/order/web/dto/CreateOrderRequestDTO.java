package se.jensen.yuki.userorderservice.order.web.dto;

import java.util.List;

public record CreateOrderRequestDTO(List<OrderItemDTO> items, ShippingInfoDTO shippingInfo, String status) {
}
