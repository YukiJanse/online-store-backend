package se.jensen.yuki.productservice.web.dto;

public record InventoryResponseDTO(
        Long productId,
        int availableStock,
        boolean available
) {
}
