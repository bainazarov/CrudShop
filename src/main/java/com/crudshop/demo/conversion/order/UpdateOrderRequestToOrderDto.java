package com.crudshop.demo.conversion.order;

import com.crudshop.demo.controller.order.request.UpdateOrderStatusRequest;
import com.crudshop.demo.dto.OrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateOrderRequestToOrderDto implements Converter<UpdateOrderStatusRequest, OrderDto> {
    @Override
    public OrderDto convert(UpdateOrderStatusRequest source) {
        return OrderDto.builder()
                .status(source.getStatus())
                .build();
    }
}
