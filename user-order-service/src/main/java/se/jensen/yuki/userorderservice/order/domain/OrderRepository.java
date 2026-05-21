package se.jensen.yuki.userorderservice.order.domain;

import se.jensen.yuki.userorderservice.order.domain.vo.OrderId;

public interface OrderRepository {
    void save(Order order);
    Order findById(OrderId id);

    void deleteById(Long orderId);
}
