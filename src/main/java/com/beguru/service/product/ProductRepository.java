package com.beguru.service.product;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private Map<Long,ProductEntity> entities = new HashMap<>(Map.of(
        1L, ProductEntity.builder().id(1L).description(LocalDateTime.now().toString()).build(),
        2L, ProductEntity.builder().id(2L).description(LocalDateTime.now().toString()).build(),
        3L, ProductEntity.builder().id(3L).description(LocalDateTime.now().toString()).build(),        
        4L, ProductEntity.builder().id(4L).description(LocalDateTime.now().toString()).build()
        ));


    public Optional<ProductEntity> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }


    public ProductEntity save(ProductEntity productEntity) {
        Long newId = entities.keySet().stream().mapToLong(Long::longValue).max().orElse(0)+1;
        productEntity.setId(newId);
        entities.put(newId, productEntity);
        return entities.get(newId);
    }


    public Collection<ProductEntity> findAll() {
        return entities.values();
    }


    public boolean existsById(Long id) {
        return entities.containsKey(id);
    }


    public void deleteById(Long id) {
        entities.remove(id);
    }

}
