package se.jensen.yuki.onlinestorebackend.product.infrastructure;

import jakarta.persistence.*;
import jakarta.validation.OverridesAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.jensen.yuki.onlinestorebackend.product.model.Rating;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductJpaEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String image;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "rate", column = @Column(name = "rating_rate")),
        @AttributeOverride(name = "count", column = @Column(name = "rating_count"))
    })
    private Rating rating;
}
