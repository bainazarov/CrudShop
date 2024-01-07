package com.crudshop.demo.controller.order.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChangeAddressRequest {

    @NotBlank(message = "Delivery address can not be blank")
    String deliveryAddress;
}
