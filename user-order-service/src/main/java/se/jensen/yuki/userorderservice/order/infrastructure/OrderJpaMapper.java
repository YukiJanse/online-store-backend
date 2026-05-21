package se.jensen.yuki.userorderservice.order.infrastructure;

import org.springframework.stereotype.Component;
import se.jensen.yuki.userorderservice.order.domain.Order;
import se.jensen.yuki.userorderservice.order.domain.vo.OrderId;
import se.jensen.yuki.userorderservice.user.domain.vo.UserId;

@Component
public class OrderJpaMapper {
    public void toEntity(OrderJpaEntity entity, Order order) {
        entity.setUserId(order.getUserId().getValue());
        entity.setItems(order.getItems());
        entity.setShippingInfo(order.getShippingInfo());
        entity.setStatus(order.getStatus());
    }

    public Order toDomain(OrderJpaEntity entity) {
        return Order.reconstruct(
                OrderId.of(entity.getId()),
                UserId.of(entity.getUserId()),
                entity.getItems(),
                entity.getShippingInfo(),
                entity.getStatus(),
                entity.getOrderedAt()
        );
    }
}
