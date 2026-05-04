package se.jensen.yuki.onlinestorebackend.user_order.order.domain;

import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderId;

public interface OrderRepository {
    void save(Order order);
    Order findById(OrderId id);

    void deleteById(Long orderId);
}
