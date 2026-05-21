package se.jensen.yuki.userorderservice.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.userorderservice.order.domain.Order;
import se.jensen.yuki.userorderservice.order.domain.OrderRepository;
import se.jensen.yuki.userorderservice.order.web.dto.CreateOrderRequestDTO;
import se.jensen.yuki.userorderservice.order.web.mapper.OrderCommandMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderCommandMapper mapper;

    @Transactional
    public void execute(Long userId, CreateOrderRequestDTO requestDTO) {
        Order order = mapper.toDomain(userId, requestDTO);
        orderRepository.save(order);
    }
}
