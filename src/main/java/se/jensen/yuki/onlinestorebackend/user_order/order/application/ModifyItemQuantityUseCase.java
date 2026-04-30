package se.jensen.yuki.onlinestorebackend.user_order.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.Order;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.OrderRepository;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderId;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.ModifyItemQuantityRequestDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderDetailDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.mapper.OrderCommandMapper;

@Service
@RequiredArgsConstructor
public class ModifyItemQuantityUseCase {
    private final OrderRepository orderRepository;
    private final OrderCommandMapper mapper;

    @Transactional
    public OrderDetailDTO execute(Long orderId, ModifyItemQuantityRequestDTO requestDTO) {
        Order order = orderRepository.findById(OrderId.of(orderId));

        order.changeQuantity(requestDTO.productId(), requestDTO.quantity());

        orderRepository.save(order);

        return mapper.toOrderDetailDTO(order);
    }
}
