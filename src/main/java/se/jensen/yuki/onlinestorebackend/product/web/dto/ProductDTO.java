package se.jensen.yuki.onlinestorebackend.product.web.dto;

import se.jensen.yuki.onlinestorebackend.product.model.Rating;

public record ProductDTO(
        Integer id,
        String title,
        Double price,
        String description,
        String category,
        String image,
        Rating rating
) {
}
