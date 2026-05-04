package se.jensen.yuki.onlinestorebackend.user_order.order.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.jensen.yuki.onlinestorebackend.shared.exception.OrderNotFoundException;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.Order;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.OrderRepository;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderId;

@Repository
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderJpaMapper mapper;

    @Override
    public void save(Order order) {
        OrderJpaEntity entity;
        if (order.getId() == null) {
            entity = new OrderJpaEntity();
        } else {
            entity = orderJpaRepository.findById(order.getId().getValue())
                    .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + order.getId()));
        }
        mapper.toEntity(entity, order);
        orderJpaRepository.save(entity);
    }

    @Override
    public Order findById(OrderId id) {
        return orderJpaRepository.findById(id.getValue())
                .map(mapper::toDomain)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    @Override
    public void deleteById(Long orderId) {
        orderJpaRepository.deleteById(orderId);
    }
}
