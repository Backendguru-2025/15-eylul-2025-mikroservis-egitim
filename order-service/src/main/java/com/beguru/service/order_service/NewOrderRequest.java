package com.beguru.service.order_service;

public record NewOrderRequest(
    Long productId,
    Integer quantity,
    Integer userId
) {

}
