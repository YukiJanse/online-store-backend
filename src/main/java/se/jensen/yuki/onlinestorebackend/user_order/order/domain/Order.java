package se.jensen.yuki.onlinestorebackend.user_order.order.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.jensen.yuki.onlinestorebackend.shared.exception.CannotModifyOrderException;
import se.jensen.yuki.onlinestorebackend.shared.exception.OrderNotFoundException;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderId;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderItem;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.ShippingInfo;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.UserId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    private OrderId id;
    private UserId userId;
    private List<OrderItem> items;
    private ShippingInfo shippingInfo;
    private OrderStatus status;
    private Instant orderedAt;

    private Order(OrderId id, UserId userId, List<OrderItem> items, ShippingInfo shippingInfo, OrderStatus status, Instant orderedAt) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.shippingInfo = shippingInfo;
        this.status = status;
        this.orderedAt = orderedAt;
    }

    public static Order create(UserId userId, List<OrderItem> items, ShippingInfo shippingInfo, OrderStatus status, Instant orderedAt) {
        return new Order(null, userId, items, shippingInfo, status, orderedAt);
    }

    public static Order reconstruct(OrderId id, UserId userId, List<OrderItem> items, ShippingInfo shippingInfo, OrderStatus status, Instant orderedAt) {
        return new Order(id, userId, items, shippingInfo, status, orderedAt);
    }

    public void changeQuantity(Long productId, int newQuantity) {
        // Validate newQuantity
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        } else if (newQuantity == 0) { // delete the item from items if quantity is 0
            deleteItem(productId);
            return;
        }

        // Validate productId
        if (productId == null || productId < 1) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        for (int i = 0; i < items.size(); i++) {
            OrderItem targetItem = items.get(i);

            if (targetItem.getProductId().equals(productId)) {
                if (targetItem.getQuantity() == newQuantity) {
                    throw new IllegalArgumentException("New quantity must be different");
                }

                items.set(i, OrderItem.of(
                        targetItem.getProductId(),
                        targetItem.getTitle(),
                        targetItem.getPrice(),
                        targetItem.getImage(),
                        newQuantity
                ));
                return;
            }
        }

        throw new OrderNotFoundException("Item not found");
    }

    public void deleteItem(Long productId) {
        if (productId == null || productId < 1) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        for (int i = 0; i < items.size(); i++) {
            OrderItem targetItem = items.get(i);

            if (targetItem.getProductId().equals(productId)) {

                items.remove(i);
                return;
            }
        }

        throw new OrderNotFoundException("Item not found");
    }

    public void changeShippingInfo(ShippingInfo newInfo) {
        if (shippingInfo.equals(newInfo)) {
            throw new IllegalArgumentException("New shipping info can not be the same info");
        }

        this.shippingInfo = newInfo;
    }

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void changeStatus(OrderStatus newStatus) {
        if (status.equals(newStatus)) {
            throw new IllegalArgumentException("Status is already status: " + newStatus);
        }

        status = newStatus;
    }

    public void cancelOrder() {
        if (!status.canCancel()) {
            throw new IllegalArgumentException("Order can not cancel");
        }
        status = OrderStatus.CANCELLED;
    }
}
