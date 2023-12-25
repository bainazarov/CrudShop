package com.crudshop.demo.controller.order;


import com.crudshop.demo.controller.order.request.CreateOrderRequest;
import com.crudshop.demo.controller.order.request.UpdateOrderRequest;
import com.crudshop.demo.controller.order.response.GetOrderResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping("/orders")
public interface OrderController {


    @PostMapping
    UUID createOrder(@RequestParam final UUID customerId,
                     @RequestBody @Valid final CreateOrderRequest request);

    @PutMapping("/{orderId}")
    GetOrderResponse updateStatusOnOrder(@PathVariable final UUID orderId,
                                         @RequestBody final UpdateOrderRequest request);
}
