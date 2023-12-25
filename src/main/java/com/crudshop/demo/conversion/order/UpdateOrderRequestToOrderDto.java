package com.crudshop.demo.conversion.order;

import com.crudshop.demo.controller.order.request.UpdateOrderRequest;
import com.crudshop.demo.dto.OrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateOrderRequestToOrderDto implements Converter<UpdateOrderRequest, OrderDto> {
    @Override
    public OrderDto convert(UpdateOrderRequest source) {
        return OrderDto.builder()
                .status(source.getStatus())
                .build();
    }
}
