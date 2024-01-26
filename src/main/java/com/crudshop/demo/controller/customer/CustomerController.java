package com.crudshop.demo.controller.customer;

import com.crudshop.demo.controller.customer.request.CreateCustomerRequest;
import com.crudshop.demo.controller.customer.response.GetCustomerOrders;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/customers")
public interface CustomerController {

    @PostMapping
    UUID createCustomer(@RequestBody @Valid final CreateCustomerRequest request);

    @GetMapping("/{userId}")
    List<GetCustomerOrders> getOrdersByCustomerId (@PathVariable final UUID userId);
}
