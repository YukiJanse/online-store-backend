package se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo;

import lombok.Getter;


@Getter
public class OrderId {
    private final Long value;

    private OrderId(Long value) {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("OrderId must be a positive number");
        }

        this.value = value;
    }

    public static OrderId of(Long value) {
        return new OrderId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderId orderId)) return false;

        return value.equals(orderId.getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() { return value.toString(); }
}
