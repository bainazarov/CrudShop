package com.crudshop.demo.controller.order.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateOrderRequest {

    @NotBlank(message = "Delivery address can not be blank")
    String deliveryAddress;

    @NotEmpty(message = "Products list can not be empty")
    @Valid
    List<OrderedProductInfo> products;

}
