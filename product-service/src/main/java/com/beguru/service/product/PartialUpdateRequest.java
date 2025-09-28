package com.beguru.service.product;

import java.math.BigDecimal;

public record PartialUpdateRequest(
        String description,
        BigDecimal price
) {
}
