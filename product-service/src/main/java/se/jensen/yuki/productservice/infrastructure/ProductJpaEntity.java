package se.jensen.yuki.productservice.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.jensen.yuki.productservice.model.Rating;

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

    @Column(nullable = false)
    private int inventory;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "rate", column = @Column(name = "rating_rate")),
        @AttributeOverride(name = "count", column = @Column(name = "rating_count"))
    })
    private Rating rating;
}
