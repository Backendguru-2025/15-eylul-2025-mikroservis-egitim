package com.beguru.service.order_service.controller;

import com.beguru.service.order_service.saga.OrderChreographyService;
import com.beguru.service.order_service.saga.SagaOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v3/orders")
@RequiredArgsConstructor // Lombok constructor enjeksiyonu için bunu ekleyin
public class OrderControllerV3 {

    private final OrderChreographyService orderChreographyService;

    //@RolesAllowed("orders.write")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP durumunu 201 olarak ayarlar
    public String createProduct(@RequestBody SagaOrderRequest order) {
        return orderChreographyService.createOrder(order);
    }

}
