package se.jensen.yuki.productservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
