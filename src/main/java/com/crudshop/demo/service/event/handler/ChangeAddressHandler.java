package com.crudshop.demo.service.event.handler;

import com.crudshop.demo.controller.event.request.ChangeAddressEventData;
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
public class ChangeAddressHandler implements EventHandler<ChangeAddressEventData> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.CHANGE_ADDRESS.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(ChangeAddressEventData eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        orderService.changeAddressOnOrder(eventSource.getOrderId(), eventSource.getDeliveryAddress());

        return "Адрес у заказа с id " + eventSource.getOrderId() + " успешно изменен на " + eventSource.getDeliveryAddress();
    }
}
