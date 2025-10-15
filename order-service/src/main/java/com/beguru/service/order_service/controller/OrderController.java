package com.beguru.service.order_service.controller;

import com.beguru.service.order_service.NewOrderRequest;
import com.beguru.service.order_service.OrderResponse;
import com.beguru.service.order_service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor // Lombok constructor enjeksiyonu için bunu ekleyin
public class OrderController{

    private final OrderService orderService;

    @GetMapping(value = "/{orderId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) throws Exception{
        Optional<OrderResponse> order = orderService.getOrderById(orderId);
        return order.map(ResponseEntity::ok) // Eğer varsa, 200 OK içine sar
                .orElse(ResponseEntity.notFound().build()); // Eğer yoksa, 404 döndür
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    //@RolesAllowed("orders.write")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP durumunu 201 olarak ayarlar
    public OrderResponse createProduct(@RequestBody NewOrderRequest order) {
        return orderService.createOrder(order);
    }

}
