package com.beguru.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{productId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProducts(@PathVariable Long productId){
    Optional<ProductResponse> product = productService.getProductById(productId);
        log.info("Product Id Querird : {}", productId);
        return product.map(ResponseEntity::ok) // Eğer varsa, 200 OK içine sar
                      .orElse(ResponseEntity.notFound().build()); // Eğer yoksa, 404 döndür
    }
   
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP durumunu 201 olarak ayarlar
    public ProductResponse createProduct(@RequestBody NewProductRequest product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean isDeleted = productService.deleteProduct(productId);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest updateRequest) {
        Optional<ProductResponse> updated = productService.updateProduct(productId, updateRequest);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> partialUpdateProduct(
            @PathVariable Long productId,
            @RequestBody PartialUpdateRequest patchRequest) {
        Optional<ProductResponse> patched = productService.patchProduct(productId, patchRequest);
        log.info("Partially updating product with ID: {}", productId);
        return patched.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
}