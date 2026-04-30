package se.jensen.yuki.onlinestorebackend.user_order.order.infrastructure;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.OrderStatus;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderItem;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.ShippingInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class OrderJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> items = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "shipping_name")),
            @AttributeOverride(name = "address.street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "address.city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "address.postalCode", column = @Column(name = "shipping_postal_code")),
            @AttributeOverride(name = "address.country", column = @Column(name = "shipping_country"))
    })
    private ShippingInfo shippingInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @CreatedDate
    @Column(name = "ordered_at", nullable = false, updatable = false)
    private Instant orderedAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
