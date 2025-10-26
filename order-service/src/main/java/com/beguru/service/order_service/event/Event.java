package com.beguru.service.order_service.event;

import com.beguru.service.order_service.saga.SagaOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String type;
    private SagaOrderRequest orderRequest;
}
