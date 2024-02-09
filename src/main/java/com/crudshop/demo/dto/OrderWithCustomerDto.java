package com.crudshop.demo.dto;

import com.crudshop.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class OrderWithCustomerDto {

    UUID id;

    CustomerDto customer;

    BigDecimal totalPrice;

    OrderStatus status;

    String deliveryAddress;
}
