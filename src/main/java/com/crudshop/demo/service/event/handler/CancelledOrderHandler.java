package com.crudshop.demo.service.event.handler;

import com.crudshop.demo.controller.event.request.CancelOrderEventData;
import com.crudshop.demo.controller.event.request.Event;
import com.crudshop.demo.controller.event.request.EventSource;
import com.crudshop.demo.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelledOrderHandler implements EventHandler<CancelOrderEventData>{

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.CANCEL_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(CancelOrderEventData eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        orderService.cancelOrderById(eventSource.getOrderId());

        return "Заказ с id " + eventSource.getOrderId() + " успешно отменен";
    }
}
