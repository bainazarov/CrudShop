package com.crudshop.demo.controller.order;


import com.crudshop.demo.controller.order.request.ChangeAddressRequest;
import com.crudshop.demo.controller.order.request.CreateOrderRequest;
import com.crudshop.demo.controller.order.request.OrderedProductInfo;
import com.crudshop.demo.controller.order.request.UpdateOrderStatusRequest;
import com.crudshop.demo.controller.order.response.GetOrderAndProductIDResponse;
import com.crudshop.demo.controller.order.response.GetOrderResponse;
import com.crudshop.demo.entity.projection.ProductProjection;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RequestMapping("/orders")
public interface OrderController {


    @PostMapping
    UUID createOrder(@RequestParam final UUID customerId,
                     @RequestBody @Valid final CreateOrderRequest request,
                     @RequestHeader(name = "idempotency-key") final String key);

    @PutMapping("/{orderId}")
    GetOrderResponse updateStatusOnOrder(@PathVariable final UUID orderId,
                                         @RequestBody final UpdateOrderStatusRequest request);

    @GetMapping()
    GetOrderAndProductIDResponse getOrdersByProductId();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrderById(@PathVariable final UUID id);

    @GetMapping("/{orderId}")
    List<ProductProjection> getOrderById(@PathVariable final UUID orderId,@RequestHeader final UUID customerId);

    @PostMapping("/{orderId}")
    UUID addProductsToExistingOrder(@PathVariable final UUID orderId,
            @RequestBody final List<OrderedProductInfo> products);

    @PostMapping("/changeAddress/{orderId}")
    UUID changeAddressOnOrder(@PathVariable final UUID orderId,
                              @RequestBody final ChangeAddressRequest request);
}
