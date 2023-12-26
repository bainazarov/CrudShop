package com.crudshop.demo.service.order;

import com.crudshop.demo.controller.order.request.OrderedProductInfo;
import com.crudshop.demo.controller.order.response.GetOrderAndProductIDResponse;
import com.crudshop.demo.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface OrderService {
    UUID createOrder(final UUID customerId, List<OrderedProductInfo> productIds);

    OrderDto updateStatusOnOrder(final UUID orderId, final OrderDto orderDto);

    GetOrderAndProductIDResponse getOrdersByProductId();
}
