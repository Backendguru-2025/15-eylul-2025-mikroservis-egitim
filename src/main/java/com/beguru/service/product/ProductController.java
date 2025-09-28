package com.beguru.service.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    //TO-DO UPDATE
    //TO-DO UPDATE
    //TO-DO BASH CURL
    //TO-DO JAVA 25
    
}