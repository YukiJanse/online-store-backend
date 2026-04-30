package se.jensen.yuki.onlinestorebackend.product.model;

import lombok.Getter;

@Getter
public class ProductData {
    private Long id;
    private String title;
    private double price;
    private String image;

    private ProductData(Long id, String title, double price, String image) {
        // TODO validation

        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public static ProductData of(Long id, String title, double price, String image) {
        return new ProductData(id, title, price, image);
    }
}
