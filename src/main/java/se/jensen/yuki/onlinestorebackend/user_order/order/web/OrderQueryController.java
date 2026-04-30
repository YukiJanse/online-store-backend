package se.jensen.yuki.onlinestorebackend.user_order.order.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.onlinestorebackend.security.service.CurrentUserProvider;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.OrderQueryService;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderDetailDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderSummaryDTO;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderQueryController {
    private final OrderQueryService orderQueryService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public ResponseEntity<List<OrderSummaryDTO>> getOrders() {
        return ResponseEntity.ok()
                .body(orderQueryService.requireJpaByUserId(currentUserProvider.currentUserId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailDTO> getOrder(@RequestParam Long orderId) {
        Long userId = currentUserProvider.currentUserId();
        return ResponseEntity.ok()
                .body(orderQueryService.requireJpaByOrderId(orderId, userId));
    }
}
