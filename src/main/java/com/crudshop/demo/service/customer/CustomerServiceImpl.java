package com.crudshop.demo.service.customer;

import com.crudshop.demo.dto.CustomerDto;
import com.crudshop.demo.dto.OrderDto;
import com.crudshop.demo.entity.CustomerEntity;
import com.crudshop.demo.entity.OrderEntity;
import com.crudshop.demo.exception.EmailAlreadyExistsException;
import com.crudshop.demo.repository.CustomerRepository;
import com.crudshop.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public UUID createCustomer(final CustomerDto customerDto) {
        final Optional<CustomerEntity> existingProduct = customerRepository.findByEmail(customerDto.getEmail());
        existingProduct.ifPresent(entity -> {
            throw new EmailAlreadyExistsException
                    ("Пользователь с email " + entity.getEmail() + " уже существует");
        });
        final CustomerEntity customer = CustomerEntity.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .build();

        return customerRepository.save(customer).getId();
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(final UUID customerId) {
        final List<OrderEntity> orders = orderRepository.findOrdersByCustomerId(customerId);
        return orders.stream()
                .map(orderEntity -> OrderDto.builder()
                        .id(orderEntity.getId())
                        .totalPrice(orderEntity.getTotalPrice())
                        .status(orderEntity.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
