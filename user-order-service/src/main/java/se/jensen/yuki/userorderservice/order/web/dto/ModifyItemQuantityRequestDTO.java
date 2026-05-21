package se.jensen.yuki.userorderservice.order.web.dto;

public record ModifyItemQuantityRequestDTO(Long productId, int quantity) {
}
