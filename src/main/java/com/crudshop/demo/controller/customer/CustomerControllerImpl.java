package com.crudshop.demo.controller.customer;

import com.crudshop.demo.controller.customer.request.CreateCustomerRequest;
import com.crudshop.demo.controller.customer.response.GetCustomerOrders;
import com.crudshop.demo.dto.CustomerDto;
import com.crudshop.demo.dto.OrderDto;
import com.crudshop.demo.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;
    private final ConversionService conversionService;


    @Override
    public UUID createProduct(final CreateCustomerRequest request) {
        final CustomerDto customerDto = conversionService.convert(request, CustomerDto.class);
        final UUID createdCustomerId = customerService.createCustomer(customerDto);
        log.info("Создали пользователя с id " + createdCustomerId);

        return createdCustomerId;
    }

    @Override
    public List<GetCustomerOrders> getOrdersByCustomerId(@PathVariable final UUID userId) {
        final List<OrderDto> orders = customerService.getOrdersByCustomerId(userId);
        log.info("Получили заказы у пользователя с ID " + userId);

        return orders.stream()
                .map(orderDto -> conversionService.convert(orderDto, GetCustomerOrders.class))
                .collect(Collectors.toList());
    }
}
