package com.crudshop.demo.controller.order.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderedProductInfo {

    @NotNull(message = "Id can not be null")
    UUID id;

    @NotNull(message = "Quantity can not be null")
    @Positive(message = "Quantity can not be negative")
    Integer quantity;
}
