package se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.jensen.yuki.onlinestorebackend.product.model.ProductData;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int quantity;

    private OrderItem(Long productId, String title, BigDecimal price, String image, int quantity) {
        if (productId == null || productId < 1) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title can not be empty");
        }
        System.out.println("Price: " + price);
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be a positive number");
        }

        if (image == null || image.isBlank()) {
            throw new IllegalArgumentException("Image can not be empty");
        }

        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be a positive number");
        }

        this.productId = productId;
        this.title = title;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public static OrderItem of(Long productId, String title, BigDecimal price, String image, int quantity) {
        return new OrderItem(productId, title, price, image, quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price, image, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof OrderItem item)) return false;
        
        return productId.equals(item.getProductId())
                && title.equals(item.getTitle())
                && price.compareTo(item.getPrice()) == 0
                && image.equals(item.getImage())
                && quantity == item.getQuantity();
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
