package com.crudshop.demo.conversion.customer;

import com.crudshop.demo.controller.customer.response.GetCustomerOrders;
import com.crudshop.demo.dto.OrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoToGetCustomerOrders implements Converter<OrderDto, GetCustomerOrders> {
    @Override
    public GetCustomerOrders convert(OrderDto source) {
        return GetCustomerOrders.builder()
                .id(source.getId())
                .totalPrice(source.getTotalPrice())
                .status(source.getStatus())
                .build();
    }
}
