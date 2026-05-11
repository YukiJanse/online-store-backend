package se.jensen.yuki.onlinestorebackend.product.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.onlinestorebackend.product.infrastructure.FakeStoreClient;
import se.jensen.yuki.onlinestorebackend.product.infrastructure.ProductJpaRepository;
import se.jensen.yuki.onlinestorebackend.product.web.dto.ProductDTO;
import se.jensen.yuki.onlinestorebackend.product.web.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final FakeStoreClient fakeStoreClient;
    private final ProductJpaRepository productJpaRepository;
    private final ProductMapper mapper;

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productDTOList;
        try {
            productDTOList = fakeStoreClient.fetchProducts();
        } catch (RuntimeException e) {
            productDTOList = productJpaRepository
                    .findAll()
                    .stream()
                    .map(mapper::toProductDTO)
                    .toList();
        }

        return productDTOList;
    }

    public void fetchProductsToDatabase() {
        List<ProductDTO> productDTOList = fakeStoreClient.fetchProducts();

        productJpaRepository.saveAll(
                productDTOList
                        .stream()
                        .map(mapper::toProductJpaEntity)
                        .toList()
        );
    }
}
