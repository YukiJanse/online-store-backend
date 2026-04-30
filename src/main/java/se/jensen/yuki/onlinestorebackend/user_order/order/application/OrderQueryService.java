package se.jensen.yuki.onlinestorebackend.user_order.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import se.jensen.yuki.onlinestorebackend.shared.exception.OrderNotFoundException;
import se.jensen.yuki.onlinestorebackend.user_order.order.infrastructure.OrderJpaEntity;
import se.jensen.yuki.onlinestorebackend.user_order.order.infrastructure.OrderJpaRepository;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderDetailDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderSummaryDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.mapper.OrderQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderQueryMapper mapper;


    public List<OrderSummaryDTO> requireJpaByUserId(Long userId) {
        return orderJpaRepository.findByUserId(userId)
                .stream()
                .map(mapper::toSummaryDto)
                .toList();
    }

    public OrderDetailDTO requireJpaByOrderId(Long orderId, Long userId) {
        OrderJpaEntity targetEntity = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (!targetEntity.getUserId().equals(userId)) {
            throw new AccessDeniedException("Order does not belong to this user");
        }

        return mapper.toDetailDTO(targetEntity);
    }
}
