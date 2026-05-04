package se.jensen.yuki.onlinestorebackend.user_order.order.web;

import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.AddressDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderDetailDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderItemDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.ShippingInfoDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class TestDataFactory {
    public static OrderDetailDTO orderDetail() {
        return OrderDetailDTO.builder()
                    .id(1L)
                    .items(List.of(
                            OrderItemDTO.builder()
                                    .productId(101L)
                                    .title("Headphones")
                                    .price(new BigDecimal("79.99"))
                                    .image("img")
                                    .quantity(2)
                                    .build()
                    ))
                    .shippingInfo(
                            ShippingInfoDTO.builder()
                                    .name("Yuki")
                                    .address(
                                            AddressDTO.builder()
                                                    .street("Street")
                                                    .postalCode("12345")
                                                    .city("Stockholm")
                                                    .country("Sweden")
                                                    .build()
                                    )
                                    .build()
                    )
                    .orderedAt(Instant.now())
                    .totalAmount(new BigDecimal("159.98"))
                    .status("PENDING")
                    .build();
    }
}
