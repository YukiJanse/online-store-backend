package se.jensen.yuki.productservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.productservice.application.ProductService;
import se.jensen.yuki.productservice.web.dto.ProductDTO;

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

    @PostMapping("/sync")
    public ResponseEntity<Void> fetchProductsToDatabase() {
        productService.fetchProductsToDatabase();

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/inventory/{productId}")
    public ResponseEntity<Void> checkInventory(@PathVariable Long productId, @RequestParam int quantity) {
        productService.hasEnoughInventory(productId, quantity);

        return ResponseEntity.ok().build();
    }
}
