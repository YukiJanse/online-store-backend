package se.jensen.yuki.onlinestorebackend.user_order.order.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.onlinestorebackend.security.service.CurrentUserProvider;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.CancelOrderUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.CreateOrderUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.ModifyItemQuantityUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.ModifyOrderShippingInfoUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.CreateOrderRequestDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.ModifyItemQuantityRequestDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderDetailDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.ShippingInfoDTO;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderCommandController {
    private final CreateOrderUseCase createOrderUseCase;
    private final ModifyItemQuantityUseCase modifyItemQUantityUseCase;
    private final ModifyOrderShippingInfoUseCase modifyOrderShippingInfoUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequestDTO requestDTO) {
        Long userId = currentUserProvider.currentUserId();
        createOrderUseCase.execute(userId, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}/quantity")
    public ResponseEntity<OrderDetailDTO> modifyItemQuantity(@RequestParam Long orderId,
                                                             @RequestBody ModifyItemQuantityRequestDTO modifyItemQuantityRequestDTO) {
        return ResponseEntity.ok()
                .body(modifyItemQUantityUseCase.execute(orderId, modifyItemQuantityRequestDTO));
    }

    @PutMapping("/{orderId}/shipping-info")
    public ResponseEntity<OrderDetailDTO> modifyShippingInfo(@RequestParam Long orderId,
                                                             @RequestBody ShippingInfoDTO shippingInfoDTO) {
        return ResponseEntity.ok()
                .body(modifyOrderShippingInfoUseCase.execute(orderId, shippingInfoDTO));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@RequestParam Long orderId) {
        Long userId = currentUserProvider.currentUserId();
        cancelOrderUseCase.execute(orderId, userId);
        return ResponseEntity.noContent().build();
    }
}
