package com.crudshop.demo.controller.event.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventOrderResult {

    private String processId;
}
