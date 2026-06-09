package se.jensen.yuki.userorderservice.order.application;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.userorderservice.order.domain.Order;
import se.jensen.yuki.userorderservice.order.domain.OrderRepository;
import se.jensen.yuki.userorderservice.order.infrastructure.ProductClient;
import se.jensen.yuki.userorderservice.order.web.dto.*;
import se.jensen.yuki.userorderservice.order.web.mapper.OrderCommandMapper;
import se.jensen.yuki.userorderservice.shared.exception.InventoryNotEnoughException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderCommandMapper mapper;
    private final ProductClient productClient;

    @Transactional
    public void execute(Long userId, CreateOrderRequestDTO requestDTO,
                        HttpServletRequest request
    ) {
        List<ReserveItemDTO> items = requestDTO.items()
                .stream()
                .map(item -> new ReserveItemDTO(item.productId(), item.quantity()))
                .toList();

        ReserveInventoryRequestDTO reserveInventoryRequestDTO =
                new ReserveInventoryRequestDTO(items);

        ReserveInventoryResponseDTO response =
                productClient.reserveInventory(
                        reserveInventoryRequestDTO,
                        request.getHeader("Authorization")
                );

        if (!response.success()) {
            throw new InventoryNotEnoughException(" Inventory is not enough!");
        }

        Order order = mapper.toDomain(userId, requestDTO);
        orderRepository.save(order);
    }
}
