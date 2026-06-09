package se.jensen.yuki.productservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.productservice.infrastructure.FakeStoreClient;
import se.jensen.yuki.productservice.infrastructure.ProductJpaEntity;
import se.jensen.yuki.productservice.infrastructure.ProductJpaRepository;
import se.jensen.yuki.productservice.shared.exception.InventoryNotEnoughException;
import se.jensen.yuki.productservice.shared.exception.ProductNotFoundException;
import se.jensen.yuki.productservice.web.dto.*;
import se.jensen.yuki.productservice.web.mapper.ProductMapper;

import java.util.List;

@Slf4j
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

    public InventoryResponseDTO hasEnoughInventory(Long productId, int quantity) {
        log.info("Starting to check inventory with id: {}", productId);
        ProductJpaEntity product = productJpaRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        int availableStock = product.getInventory();

        return new InventoryResponseDTO(productId, availableStock, availableStock >= quantity);
    }

    public ProductDTO getProductById(Long id) {
        return fakeStoreClient.getProductById(id);
    }

    @Transactional
    public ReserveInventoryResponseDTO reserveInventory(ReserveInventoryRequestDTO requestDTO) {
        log.info("Starting to reserve inventory");
        for (ReserveItemDTO item : requestDTO.items()) {
            ProductJpaEntity product = productJpaRepository
                    .findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            int availableStock = product.getInventory();

            if (availableStock < item.quantity()) {
                throw new InventoryNotEnoughException("Inventory is not enough! ProductId: " + item.productId());
            }
        }

        for (ReserveItemDTO item : requestDTO.items()) {
            ProductJpaEntity product = productJpaRepository
                    .findById(item.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            int availableStock = product.getInventory();

            product.setInventory(availableStock - item.quantity());
        }

        return new ReserveInventoryResponseDTO(true, requestDTO.items());
    }
}
