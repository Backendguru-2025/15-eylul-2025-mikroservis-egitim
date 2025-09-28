package com.beguru.service.product;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    //private List<Image> images;
    private Long id;
    private String description;
    private String name;
    private BigDecimal price;

}
