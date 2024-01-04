package com.crudshop.demo.controller.order;

import com.crudshop.demo.controller.order.request.ChangeAddressRequest;
import com.crudshop.demo.controller.order.request.CreateOrderRequest;
import com.crudshop.demo.controller.order.request.OrderedProductInfo;
import com.crudshop.demo.controller.order.request.UpdateOrderStatusRequest;
import com.crudshop.demo.controller.order.response.GetOrderAndProductIDResponse;
import com.crudshop.demo.controller.order.response.GetOrderResponse;
import com.crudshop.demo.dto.OrderDto;
import com.crudshop.demo.entity.projection.ProductProjection;
import com.crudshop.demo.exception.OrderNotFoundException;
import com.crudshop.demo.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    public UUID createOrder(final UUID customerId,
                            final CreateOrderRequest request) {
        final UUID requestId = orderService.createOrder(customerId, request.getDeliveryAddress(), request.getProducts());
        log.info("Создали заказ на адрес " + request.getDeliveryAddress());

        return requestId;
    }

    @Override
    public GetOrderResponse updateStatusOnOrder(@PathVariable final UUID orderId,
                                                @RequestBody final UpdateOrderStatusRequest request) {
        final OrderDto updatedOrder = orderService.updateStatusOnOrder(orderId, request.getStatus());
        final GetOrderResponse updateStatus = conversionService.convert(updatedOrder, GetOrderResponse.class);
        log.info("Изменили статус заказа на " + request.getStatus());

        return updateStatus;
    }

    @Override
    public GetOrderAndProductIDResponse getOrdersByProductId() {
        GetOrderAndProductIDResponse response = orderService.getOrdersByProductId();
        log.info("Получили информацию о заказанных продуктах ");

        return response;
    }

    @Override
    public ResponseEntity<Void> deleteOrderById(final UUID id) {
        try {
            orderService.cancelOrderById(id);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        log.info("Удалили заказ с id " + id);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<ProductProjection> getOrderById(final UUID orderId,final UUID customerId) {
        log.info("Получили информацию о заказе по ID " + orderId);
        return orderService.getOrderById(orderId, customerId);
    }

    @Override
    public UUID addProductsToExistingOrder(final UUID orderId,
                                   final List<OrderedProductInfo> products) {
        final UUID responseId = orderService.addProductsToExistingOrder(orderId, products);
        log.info("Добавили продукт(-ы) в заказ с ID" + orderId );

        return responseId;
    }

    @Override
    public UUID changeAddressOnOrder(final UUID orderId, final ChangeAddressRequest deliveryAddress) {
        log.info("Поменяли адрес у заказа под ID " + orderId);

        return orderService.changeAddressOnOrder(orderId, deliveryAddress);
    }
}
