package com.crudshop.demo.controller.event.request;

import com.crudshop.demo.controller.order.request.OrderedProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreateOrderEventData implements HttpEvent {

    @NotNull(message = "Customer id can not be blank")
    private final UUID customerId;

    @NotBlank(message = "Delivery address can not be blank")
    private final String deliveryAddress;

    @NotEmpty(message = "Products list can not be empty")
    @Valid
    private final List<OrderedProductInfo> products;

    @NotBlank(message = "key can not be blank")
    private final String key;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CREATE_ORDER;
    }
}
