package com.crudshop.demo.controller.event.request;

import com.crudshop.demo.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@JsonTypeName("COMPLETED_ORDER")
public class CompletedOrderEventData implements HttpEvent {

    @NotNull(message = "orderId can not be null")
    private UUID orderId;

    @NotNull(message = "Статус не может быть пустым")
    private OrderStatus status;

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Event getEvent() {
        return Event.COMPLETED_ORDER;
    }
}
