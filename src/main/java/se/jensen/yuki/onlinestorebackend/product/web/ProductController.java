package se.jensen.yuki.onlinestorebackend.product.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.onlinestorebackend.product.application.ProductService;
import se.jensen.yuki.onlinestorebackend.product.model.ProductData;
import se.jensen.yuki.onlinestorebackend.product.web.dto.ProductDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
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
}
