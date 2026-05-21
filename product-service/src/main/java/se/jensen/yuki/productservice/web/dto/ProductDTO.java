package se.jensen.yuki.productservice.web.dto;

import se.jensen.yuki.productservice.model.Rating;

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
