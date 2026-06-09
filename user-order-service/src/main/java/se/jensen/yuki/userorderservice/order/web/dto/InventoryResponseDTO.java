package se.jensen.yuki.userorderservice.order.web.dto;

public record InventoryResponseDTO(
        Long productId,
        int availableStock,
        boolean available
) {
}
