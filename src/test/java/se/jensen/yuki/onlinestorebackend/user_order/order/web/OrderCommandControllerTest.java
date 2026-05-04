package se.jensen.yuki.onlinestorebackend.user_order.order.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.jensen.yuki.onlinestorebackend.security.service.CurrentUserProvider;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.CancelOrderUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.CreateOrderUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.ModifyItemQuantityUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.application.ModifyOrderShippingInfoUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.order.web.dto.OrderDetailDTO;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderCommandControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CreateOrderUseCase createOrderUseCase;

    @MockitoBean
    ModifyItemQuantityUseCase modifyItemQuantityUseCase;

    @MockitoBean
    ModifyOrderShippingInfoUseCase modifyOrderShippingInfoUseCase;

    @MockitoBean
    CancelOrderUseCase cancelOrderUseCase;

    @MockitoBean
    CurrentUserProvider currentUserProvider;

    @Test
    void createOrder() throws Exception {
        // Arrange
        when(currentUserProvider.currentUserId()).thenReturn(1L);

        String json = """
                {
                  "items": [
                    {
                      "productId": 101,
                      "title": "Wireless Bluetooth Headphones",
                      "price": 79.99,
                      "image": "https://example.com/images/headphones.jpg",
                      "quantity": 2
                    },
                    {
                      "productId": 202,
                      "title": "Mechanical Keyboard (RGB)",
                      "price": 129.50,
                      "image": "https://example.com/images/keyboard.jpg",
                      "quantity": 1
                    }
                  ],
                  "shippingInfo": {
                    "name": "Yuki Janse",
                    "address": {
                      "street": "Sveavägen 123",
                      "postalCode": "113 50",
                      "city": "Stockholm",
                      "country": "Sweden"
                    }
                  },
                  "status": "PENDING"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                )
                .andExpect(status().isNoContent());

        verify(createOrderUseCase).execute(eq(1L), argThat(dto ->
                dto.items().size() == 2 &&
                dto.items().get(0).title().equals("Wireless Bluetooth Headphones") &&
                dto.shippingInfo().name().equals("Yuki Janse") &&
                dto.status().equals("PENDING")
        ));
    }

    @Test
    void modifyItemQuantity() throws Exception {
        // Arrange
        OrderDetailDTO response = TestDataFactory.orderDetail();

        when(modifyItemQuantityUseCase.execute(eq(1L), any())).thenReturn(response);

        String json = """
                {
                  "productId": 101,
                  "quantity": 2
                }
                """;

        // Act & Assert
        mockMvc.perform(put("/v1/orders/1/quantity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                )
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.items[0].productId").value(101L))
                .andExpect(jsonPath("$.items[0].title").value("Headphones"))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.shippingInfo.name").value("Yuki"))
                .andExpect(jsonPath("$.shippingInfo.address.city").value("Stockholm"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(status().isOk());

        verify(modifyItemQuantityUseCase)
                .execute(eq(1L), argThat(dto ->
                        dto.productId().equals(101L)
                                && dto.quantity() == 2
                ));
    }

    @Test
    void modifyShippingInfo() throws Exception {
        // Arrange
        OrderDetailDTO response = TestDataFactory.orderDetail();

        when(modifyOrderShippingInfoUseCase.execute(eq(1L), any())).thenReturn(response);

        String json = """
                {
                  "name": "Yuki",
                  "address": {
                    "street": "Sveavägen 123",
                    "postalCode": "113 50",
                    "city": "Stockholm",
                    "country": "Sweden"
                  }
                }
                """;

        // Act & Assert
        mockMvc.perform(put("/v1/orders/1/shipping-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.items[0].productId").value(101L))
                .andExpect(jsonPath("$.items[0].title").value("Headphones"))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.shippingInfo.name").value("Yuki"))
                .andExpect(jsonPath("$.shippingInfo.address.city").value("Stockholm"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(status().isOk());

        verify(modifyOrderShippingInfoUseCase)
                .execute(eq(1L), argThat(dto ->
                        dto.name().equals("Yuki")
                ));
    }

    @Test
    void deleteOrder() throws Exception {
        // Arrange
        when(currentUserProvider.currentUserId()).thenReturn(1L);

        // Act & Assert
        mockMvc.perform(delete("/v1/orders/1"))
                .andExpect(status().isNoContent());

        verify(cancelOrderUseCase).execute(eq(1L), eq(1L));
    }
}