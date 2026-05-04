package se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.mapper;

import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderId;

public interface OrderIdMapper {
    default Long map(OrderId orderId) { return orderId == null ? null : orderId.getValue(); }

    default OrderId map(Long id) { return id == null ? null : OrderId.of(id); }
}
