package se.jensen.yuki.userorderservice.order.web.dto;

import java.util.List;

public record ReserveInventoryRequestDTO(List<ReserveItemDTO> items) {
}
