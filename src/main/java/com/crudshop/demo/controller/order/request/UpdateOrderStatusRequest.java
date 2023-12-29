package com.crudshop.demo.controller.order.request;

import com.crudshop.demo.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateOrderStatusRequest {

    @NotNull(message = "Статус не может быть пустым")
    OrderStatus status;

}
