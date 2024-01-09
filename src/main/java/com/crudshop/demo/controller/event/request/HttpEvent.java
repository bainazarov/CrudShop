package com.crudshop.demo.controller.event.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderEventData.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = CancelOrderEventData.class, name = "CANCEL_ORDER"),
        @JsonSubTypes.Type(value = CompletedOrderEventData.class, name = "COMPLETED_ORDER"),
        @JsonSubTypes.Type(value = ChangeAddressEventData.class, name = "CHANGE_ADDRESS")
})
public interface HttpEvent extends EventSource {
}
