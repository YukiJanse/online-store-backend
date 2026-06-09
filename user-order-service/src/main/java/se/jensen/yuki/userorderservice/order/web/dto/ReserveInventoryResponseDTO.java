package se.jensen.yuki.userorderservice.order.web.dto;

import java.util.List;

public record ReserveInventoryResponseDTO(boolean success, List<ReserveItemDTO> items) {
}
