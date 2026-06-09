package se.jensen.yuki.productservice.web.dto;

import java.util.List;

public record ReserveInventoryRequestDTO(List<ReserveItemDTO> items) {
}
