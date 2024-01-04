package com.crudshop.demo.controller.event.request;

import com.crudshop.demo.controller.order.request.ChangeAddressRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChangeAddressEventData implements HttpEvent {

    @NotNull(message = "Id can not be null")
    UUID orderId;

    ChangeAddressRequest deliveryAddress;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CHANGE_ADDRESS;
    }
}
