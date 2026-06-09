package se.jensen.yuki.productservice.web.dto;

import java.util.List;

public record ReserveInventoryResponseDTO(boolean success, List<ReserveItemDTO> items) {
}
