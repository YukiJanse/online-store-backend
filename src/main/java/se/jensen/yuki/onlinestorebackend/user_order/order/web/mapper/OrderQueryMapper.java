package se.jensen.yuki.onlinestorebackend.user_order.order.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.onlinestorebackend.user_order.order.infrastructure.OrderJpaEntity;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderDetailDTO;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderSummaryDTO;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderQueryMapper {
    @Mapping(target = "totalAmount", expression = "java(calcTotal(entity))")
    @Mapping(target = "status", expression = "java(entity.getStatus().toString())")
    OrderSummaryDTO toSummaryDto(OrderJpaEntity entity);

    @Mapping(target = "totalAmount", expression = "java(calcTotal(entity))")
    @Mapping(target = "status", expression = "java(entity.getStatus().toString())")
    OrderDetailDTO toDetailDTO(OrderJpaEntity entity);

    default BigDecimal calcTotal(OrderJpaEntity entity) {
        return entity.getItems()
                .stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
