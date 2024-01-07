package com.crudshop.demo.dto;

import com.crudshop.demo.entity.CustomerEntity;
import com.crudshop.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class OrderDto {

    UUID id;

    CustomerEntity customer;

    BigDecimal totalPrice;

    OrderStatus status;

    String deliveryAddress;
}
