package com.crudshop.demo.dto;

import com.crudshop.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderWithCustomerDto {

    UUID id;

    CustomerDto customer;

    Double totalPrice;

    OrderStatus status;

    String deliveryAddress;
}
