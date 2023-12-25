package com.crudshop.demo.service.customer;

import com.crudshop.demo.dto.CustomerDto;
import com.crudshop.demo.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface CustomerService {
    UUID createCustomer(final CustomerDto customerDto);

    List<OrderDto> getOrdersByCustomerId(final UUID customerId);
}
