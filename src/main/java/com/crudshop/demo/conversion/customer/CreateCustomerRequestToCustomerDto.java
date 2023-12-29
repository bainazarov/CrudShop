package com.crudshop.demo.conversion.customer;

import com.crudshop.demo.controller.customer.request.CreateCustomerRequest;
import com.crudshop.demo.dto.CustomerDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerRequestToCustomerDto implements Converter<CreateCustomerRequest, CustomerDto> {
    @Override
    public CustomerDto convert(CreateCustomerRequest source) {
        return CustomerDto.builder()
                .name(source.getName())
                .email(source.getEmail())
                .build();
    }
}
