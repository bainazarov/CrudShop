package com.crudshop.demo.controller.event.request;

import com.crudshop.demo.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Data
@Builder
public class CancelledOrderEventData implements HttpEvent {

    @NotNull(message = "Id can not be null")
    UUID orderId;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CANCELLED_ORDER;
    }
}
