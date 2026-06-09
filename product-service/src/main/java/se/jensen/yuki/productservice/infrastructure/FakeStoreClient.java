package se.jensen.yuki.productservice.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.jensen.yuki.productservice.web.dto.ProductDTO;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FakeStoreClient {
    private final WebClient webClient;
    @Value("${fakestoreapi.base-url:https://fakestoreapi.com}")
    private String baseUrl;

    public List<ProductDTO> fetchProducts() {
        List<ProductDTO> productDTOList = webClient.get()
                .uri(baseUrl + "/products")
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> Mono.error(
                                new RuntimeException("FakeStore API error")
                        )
                )
                .bodyToFlux(ProductDTO.class)
                .doOnNext(product -> log.info("Received product: {}", product))
                .collectList()
                .block();

        return productDTOList;
    }

    public ProductDTO getProductById(Long id) {
        return webClient.get()
                .uri(baseUrl + "/products/" + id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> Mono.error(
                                new RuntimeException("FakeStore API error")
                        )
                )
                .bodyToMono(ProductDTO.class)
                .doOnNext(product -> log.info("Received product: {}", product))
                .block();
    }
}
