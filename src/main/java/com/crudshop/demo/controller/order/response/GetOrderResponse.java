package com.crudshop.demo.controller.order.response;

import com.crudshop.demo.entity.CustomerEntity;
import com.crudshop.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class GetOrderResponse {

    CustomerEntity customer;

    BigDecimal totalPrice;

    OrderStatus status;

    String deliveryAddress;
}
