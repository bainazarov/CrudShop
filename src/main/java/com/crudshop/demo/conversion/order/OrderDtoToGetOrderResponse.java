package com.crudshop.demo.conversion.order;

import com.crudshop.demo.controller.order.response.GetOrderResponse;
import com.crudshop.demo.dto.OrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoToGetOrderResponse implements Converter<OrderDto, GetOrderResponse> {
    @Override
    public GetOrderResponse convert(OrderDto source) {
        return GetOrderResponse.builder()
                .customer(source.getCustomer())
                .totalPrice(source.getTotalPrice())
                .status(source.getStatus())
                .build();
    }
}
