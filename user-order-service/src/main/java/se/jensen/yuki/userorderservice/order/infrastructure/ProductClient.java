package se.jensen.yuki.userorderservice.order.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.jensen.yuki.userorderservice.order.web.dto.InventoryResponseDTO;
import se.jensen.yuki.userorderservice.order.web.dto.ReserveInventoryRequestDTO;
import se.jensen.yuki.userorderservice.order.web.dto.ReserveInventoryResponseDTO;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {
    private final WebClient productWebClient;
    @Value("${productapi.base-url:http://product-service:5001}")
    private String baseUrl;

    public InventoryResponseDTO checkInventory(Long productId, int quantity, String authHeader) {
        return productWebClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/inventory/{productId}")
                    .queryParam("quantity", quantity)
                    .build(productId))
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> Mono.error(
                                new RuntimeException("Product API error")
                        )
                )
                .bodyToMono(InventoryResponseDTO.class)
                .doOnNext(response -> log.info("Received response: {}", response))
                .block();
    }

    public ReserveInventoryResponseDTO reserveInventory(ReserveInventoryRequestDTO requestDTO, String authHeader) {
        return productWebClient.put()
                .uri("/reserve")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .bodyValue(requestDTO)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    log.error("""
                                        Product API ERROR
                                        status: {}
                                        body: {}
                                        headers: {}
                                    """,
                                    response.statusCode(),
                                    body,
                                    response.headers().asHttpHeaders());

                                    return Mono.error(new RuntimeException(
                                            "Product API error: " + response.statusCode() + " body=" + body
                                    ));
                                })
                )
                .bodyToMono(ReserveInventoryResponseDTO.class)
                .doOnNext(response -> log.info("Received response: {}", response))
                .block();
    }

}
