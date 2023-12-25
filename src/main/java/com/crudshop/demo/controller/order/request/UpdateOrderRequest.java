package com.crudshop.demo.controller.order.request;

import com.crudshop.demo.entity.CustomerEntity;
import com.crudshop.demo.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateOrderRequest {

    CustomerEntity customer;

    @NotBlank(message = "Статус не может быть пустым")
    OrderStatus status;

}
