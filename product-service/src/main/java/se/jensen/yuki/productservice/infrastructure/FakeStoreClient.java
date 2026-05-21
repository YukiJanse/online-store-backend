package se.jensen.yuki.productservice.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<ProductDTO> fetchProducts() {
        List<ProductDTO> productDTOList = webClient.get()
                .uri("https://fakestoreapi.com/products")
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

        if (productDTOList == null) {
            throw new AccessDeniedException("Failed to fetch products from external API");
        }


        return productDTOList;
    }
}
