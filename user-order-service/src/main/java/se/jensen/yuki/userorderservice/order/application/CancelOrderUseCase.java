package se.jensen.yuki.userorderservice.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.userorderservice.order.domain.Order;
import se.jensen.yuki.userorderservice.order.domain.OrderRepository;
import se.jensen.yuki.userorderservice.order.domain.vo.OrderId;
import se.jensen.yuki.userorderservice.user.domain.vo.UserId;

@Service
@RequiredArgsConstructor
public class CancelOrderUseCase {
    private final OrderRepository orderRepository;

    public void execute(Long orderId, Long userId) {
        Order targetOrder = orderRepository.findById(OrderId.of(orderId));

        if (!targetOrder.getUserId().equals(UserId.of(userId))) {
            throw new IllegalArgumentException("Can not cancel other's order");
        }

        orderRepository.deleteById(orderId);
    }
}
