package com.beguru.service.order_service;

public record OrderResponse(    Long productId,
                                Integer userId,
                                Integer quantity
) {

}

