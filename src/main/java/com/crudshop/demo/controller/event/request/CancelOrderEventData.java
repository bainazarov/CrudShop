package com.crudshop.demo.controller.event.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CancelOrderEventData implements HttpEvent {

    @NotNull(message = "orderId can not be null")
    private final UUID orderId;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.CANCEL_ORDER;
    }
}
