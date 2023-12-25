package com.crudshop.demo.controller.order;

import com.crudshop.demo.controller.order.request.CreateOrderRequest;
import com.crudshop.demo.controller.order.request.UpdateOrderRequest;
import com.crudshop.demo.controller.order.response.GetOrderResponse;
import com.crudshop.demo.dto.OrderDto;
import com.crudshop.demo.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final ConversionService conversionService;

    @PostMapping
    @Override
    public UUID createOrder(@RequestParam final UUID customerId,
                            @RequestBody @Valid final CreateOrderRequest request) {
        final UUID requestId = orderService.createOrder(customerId, request.getProducts());
        log.info("Создали заказ с на адресс " + request.getDeliveryAddress());

        return requestId;
    }

    @Override
    public GetOrderResponse updateStatusOnOrder(@PathVariable final UUID orderId,
                                                @RequestBody final UpdateOrderRequest request) {
        final OrderDto updatedOrderDto = conversionService.convert(request, OrderDto.class);
        final OrderDto updatedOrder = orderService.updateStatusOnOrder(orderId, updatedOrderDto);
        final GetOrderResponse updateStatus = conversionService.convert(updatedOrder, GetOrderResponse.class);
        log.info("Изменили статус заказа на " + request.getStatus());

        return updateStatus;
    }
}
