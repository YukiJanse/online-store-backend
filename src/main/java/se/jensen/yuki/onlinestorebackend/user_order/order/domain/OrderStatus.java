package se.jensen.yuki.onlinestorebackend.user_order.order.domain;

import java.util.Arrays;

public enum OrderStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    SHIPPED("shipped"),
    DELIVERED("delivered"),
    CANCELLED("cancelled");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public boolean canCancel() {
        return this == PENDING || this == CONFIRMED;
    }

    public static OrderStatus from(String value) {
        return Arrays.stream(values())
                .filter(s -> s.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + value));
    }
}
