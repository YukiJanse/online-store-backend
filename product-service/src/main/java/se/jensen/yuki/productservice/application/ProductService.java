package se.jensen.yuki.productservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.productservice.infrastructure.FakeStoreClient;
import se.jensen.yuki.productservice.infrastructure.ProductJpaEntity;
import se.jensen.yuki.productservice.infrastructure.ProductJpaRepository;
import se.jensen.yuki.productservice.shared.exception.InsufficientInventoryException;
import se.jensen.yuki.productservice.shared.exception.ProductNotFoundException;
import se.jensen.yuki.productservice.web.dto.ProductDTO;
import se.jensen.yuki.productservice.web.mapper.ProductMapper;

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

    public void hasEnoughInventory(Long productId, int quantity) {
        ProductJpaEntity product = productJpaRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getInventory() < quantity) {
            throw new InsufficientInventoryException("The inventory is not enough for the order");
        }
    }
}
