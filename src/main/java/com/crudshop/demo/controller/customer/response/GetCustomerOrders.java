package com.crudshop.demo.controller.customer.response;

import com.crudshop.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class GetCustomerOrders {
    UUID id;

    Double totalPrice;

    OrderStatus status;
}
