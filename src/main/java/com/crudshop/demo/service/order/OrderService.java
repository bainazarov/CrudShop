package com.crudshop.demo.service.order;

import com.crudshop.demo.controller.order.request.ChangeAddressRequest;
import com.crudshop.demo.controller.order.request.OrderedProductInfo;
import com.crudshop.demo.controller.order.response.GetOrderAndProductIDResponse;
import com.crudshop.demo.dto.OrderDto;
import com.crudshop.demo.entity.OrderStatus;
import com.crudshop.demo.entity.projection.ProductProjection;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public interface OrderService {
    UUID createOrder(final UUID customerId, final String address,
                     final List<OrderedProductInfo> productIds, final String key);

    List<ProductProjection> getOrderById(final UUID orderId,final UUID customerId);

    @Transactional
    UUID addProductsToExistingOrder(final UUID orderId,final List<OrderedProductInfo> products);

    OrderDto updateStatusOnOrder(UUID orderId, OrderStatus status);

    void cancelOrderById(final UUID orderId);

    GetOrderAndProductIDResponse getOrdersByProductId();

    UUID changeAddressOnOrder(final UUID orderId, final ChangeAddressRequest newAddress);
}
