package com.crudshop.demo.controller.customer.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCustomerRequest {

    @NotBlank(message = "Name can not be blank")
    String name;

    @NotBlank(message = "Email can not be blank")
    String email;
}
