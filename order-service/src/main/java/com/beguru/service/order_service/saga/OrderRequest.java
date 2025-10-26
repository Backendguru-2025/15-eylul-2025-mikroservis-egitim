package com.beguru.service.order_service.saga;

public record OrderRequest(
        String orderId,
        String productId,
        int quantity,
        double amount
)
{

}
