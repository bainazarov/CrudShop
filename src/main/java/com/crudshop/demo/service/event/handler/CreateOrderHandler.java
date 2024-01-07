package com.crudshop.demo.service.event.handler;

import com.crudshop.demo.controller.event.request.CreateOrderEventData;
import com.crudshop.demo.controller.event.request.Event;
import com.crudshop.demo.controller.event.request.EventSource;
import com.crudshop.demo.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderHandler implements EventHandler<CreateOrderEventData> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.CREATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(CreateOrderEventData eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        UUID orderId = orderService.createOrder(eventSource.getCustomerId(), eventSource.getDeliveryAddress(),
                eventSource.getProducts());

        return "Заказ успешно создан. Идентификатор заказа: " + orderId;
    }
}
