package se.jensen.yuki.userorderservice.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.userorderservice.order.domain.Order;
import se.jensen.yuki.userorderservice.order.domain.OrderRepository;
import se.jensen.yuki.userorderservice.order.domain.vo.OrderId;
import se.jensen.yuki.userorderservice.order.domain.vo.ShippingInfo;
import se.jensen.yuki.userorderservice.order.web.dto.OrderDetailDTO;
import se.jensen.yuki.userorderservice.order.web.dto.ShippingInfoDTO;
import se.jensen.yuki.userorderservice.order.web.mapper.OrderCommandMapper;
import se.jensen.yuki.userorderservice.user.domain.vo.Address;

@Service
@RequiredArgsConstructor
public class ModifyOrderShippingInfoUseCase {
    private final OrderRepository orderRepository;
    private final OrderCommandMapper mapper;

    public OrderDetailDTO execute(Long orderId, ShippingInfoDTO shippingInfoDTO) {
        Order targetOrder = orderRepository.findById(OrderId.of(orderId));
        String newName = shippingInfoDTO.name();
        Address newAddress = Address.of(
                shippingInfoDTO.address().street(),
                shippingInfoDTO.address().postalCode(),
                shippingInfoDTO.address().city(),
                shippingInfoDTO.address().country()
        );

        ShippingInfo newShippingInfo = ShippingInfo.of(newName , newAddress);

        targetOrder.changeShippingInfo(newShippingInfo);

        orderRepository.save(targetOrder);

        return mapper.toOrderDetailDTO(targetOrder);
    }
}
