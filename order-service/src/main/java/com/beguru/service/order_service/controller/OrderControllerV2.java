package com.beguru.service.order_service.controller;


import com.beguru.service.order_service.saga.OrderOrchestratorService;
import com.beguru.service.order_service.saga.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/orders")
@RequiredArgsConstructor // Lombok constructor enjeksiyonu için bunu ekleyin
public class OrderControllerV2 {

    private final OrderOrchestratorService orderOrchestratorService;

    //@RolesAllowed("orders.write")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP durumunu 201 olarak ayarlar
    public String createProduct(@RequestBody OrderRequest order) {
        return orderOrchestratorService.placeOrder(order);
    }

}
