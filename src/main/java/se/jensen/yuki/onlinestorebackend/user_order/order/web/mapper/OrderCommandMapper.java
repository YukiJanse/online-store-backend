package se.jensen.yuki.onlinestorebackend.user_order.order.web.mapper;

import org.springframework.stereotype.Component;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.Order;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.OrderStatus;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.OrderItem;
import se.jensen.yuki.onlinestorebackend.user_order.order.domain.vo.ShippingInfo;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.*;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.Address;
import se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo.UserId;

import java.util.List;

@Component
public class OrderCommandMapper {

    public Order toDomain(Long userId, CreateOrderRequestDTO dto) {
        List<OrderItem> items = dto.items()
                .stream()
                .map(this::toOrderItem)
                .toList();
        return Order.create(
                    UserId.of(userId),
                    items,
                    toShippingInfo(dto.shippingInfo()),
                    OrderStatus.from(dto.status()),
                    null
        );
    }

    private OrderItem toOrderItem(OrderItemDTO dto) {
        return OrderItem.of(
                dto.productId(),
                dto.title(),
                dto.price(),
                dto.image(),
                dto.quantity()
        );
    }

    private ShippingInfo toShippingInfo(ShippingInfoDTO dto) {
        return ShippingInfo.of(
                dto.name(),
                toAddress(dto.address())
        );
    }
    private Address toAddress(AddressDTO dto) {
        return Address.of(
                dto.street(),
                dto.postalCode(),
                dto.city(),
                dto.country()
        );
    }

    private OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .title(orderItem.getTitle())
                .productId(orderItem.getProductId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .image(orderItem.getImage())
                .build();

    }

    private ShippingInfoDTO toShippingInfoDTO(ShippingInfo shippingInfo) {
        return ShippingInfoDTO.builder()
                .name(shippingInfo.getName())
                .address(toAddressDTO(shippingInfo.getAddress()))
                .build();
    }

    private AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }

    public OrderDetailDTO toOrderDetailDTO(Order order) {
        return OrderDetailDTO.builder()
                .id(order.getId().getValue())
                .items(order.getItems()
                        .stream()
                        .map(this::toOrderItemDTO)
                        .toList()
                )
                .orderedAt(order.getOrderedAt())
                .totalAmount(order.getTotalAmount())
                .shippingInfo(toShippingInfoDTO(order.getShippingInfo()))
                .status(order.getStatus().toString())
                .build();
    }
}
