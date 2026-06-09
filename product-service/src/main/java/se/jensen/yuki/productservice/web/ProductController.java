package se.jensen.yuki.productservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.productservice.application.ProductService;
import se.jensen.yuki.productservice.web.dto.InventoryResponseDTO;
import se.jensen.yuki.productservice.web.dto.ProductDTO;
import se.jensen.yuki.productservice.web.dto.ReserveInventoryRequestDTO;
import se.jensen.yuki.productservice.web.dto.ReserveInventoryResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity
                .ok()
                .body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(productService.getProductById(id));
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> fetchProductsToDatabase() {
        productService.fetchProductsToDatabase();

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/inventory/{productId}")
    public ResponseEntity<InventoryResponseDTO> checkInventory(@PathVariable Long productId, @RequestParam int quantity) {
        return ResponseEntity
                .ok()
                .body(productService.hasEnoughInventory(productId, quantity));
    }

    @PutMapping("/reserve")
    public ResponseEntity<ReserveInventoryResponseDTO> reserveInventory(@RequestBody ReserveInventoryRequestDTO requestDTO) {
        return ResponseEntity
                .ok()
                .body(productService.reserveInventory(requestDTO));
    }
}
