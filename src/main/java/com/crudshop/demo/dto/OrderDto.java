package com.crudshop.demo.dto;

import com.crudshop.demo.entity.CustomerEntity;
import com.crudshop.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderDto {

    UUID id;

    CustomerEntity customer;

    Double totalPrice;

    OrderStatus status;

    String deliveryAddress;
}
