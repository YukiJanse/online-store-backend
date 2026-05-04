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
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderCommandController {
    private final CreateOrderUseCase createOrderUseCase;
    private final ModifyItemQuantityUseCase modifyItemQuantityUseCase;
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
    public ResponseEntity<OrderDetailDTO> modifyItemQuantity(@PathVariable Long orderId,
                                                             @RequestBody ModifyItemQuantityRequestDTO modifyItemQuantityRequestDTO) {
        return ResponseEntity.ok()
                .body(modifyItemQuantityUseCase.execute(orderId, modifyItemQuantityRequestDTO));
    }

    @PutMapping("/{orderId}/shipp" +
            "ing-info")
    public ResponseEntity<OrderDetailDTO> modifyShippingInfo(@PathVariable Long orderId,
                                                             @RequestBody ShippingInfoDTO shippingInfoDTO) {
        return ResponseEntity.ok()
                .body(modifyOrderShippingInfoUseCase.execute(orderId, shippingInfoDTO));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        Long userId = currentUserProvider.currentUserId();
        cancelOrderUseCase.execute(orderId, userId);
        return ResponseEntity.noContent().build();
    }
}
