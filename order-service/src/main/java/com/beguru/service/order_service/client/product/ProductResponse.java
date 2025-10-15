package com.beguru.service.order_service.client.product;

import java.math.BigDecimal;

public record ProductResponse(
        long productId,
        String name,
        String description,
        BigDecimal price) {

}

