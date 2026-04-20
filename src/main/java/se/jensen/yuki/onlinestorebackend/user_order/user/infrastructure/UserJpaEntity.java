package se.jensen.yuki.onlinestorebackend.user_order.user.infrastructure;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Address;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number",unique = true, nullable = false, length = 12)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false, length = 50)
    private String role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}
