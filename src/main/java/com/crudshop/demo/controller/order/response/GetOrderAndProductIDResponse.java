package com.crudshop.demo.controller.order.response;

import com.crudshop.demo.dto.OrderWithCustomerDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Value
@Builder
public class GetOrderAndProductIDResponse {

    Map<UUID, List<OrderWithCustomerDto>> orders;
}
