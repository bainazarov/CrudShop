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
public class CompletedOrderEventData implements HttpEvent {

    @NotNull(message = "orderId can not be null")
    private final UUID orderId;

    @NotNull(message = "Статус не может быть пустым")
    private final OrderStatus status;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.COMPLETED_ORDER;
    }
}
