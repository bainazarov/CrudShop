package com.crudshop.demo.service.order;

import com.crudshop.demo.controller.order.request.OrderedProductInfo;
import com.crudshop.demo.dto.OrderDto;
import com.crudshop.demo.entity.CustomerEntity;
import com.crudshop.demo.entity.OrderEntity;
import com.crudshop.demo.entity.OrderStatus;
import com.crudshop.demo.entity.OrderedProduct;
import com.crudshop.demo.entity.OrderedProductKey;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.exception.CustomerNotFoundException;
import com.crudshop.demo.exception.NotEnoughQuantityForProductException;
import com.crudshop.demo.exception.OrderNotFoundException;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.repository.CustomerRepository;
import com.crudshop.demo.repository.OrderRepository;
import com.crudshop.demo.repository.OrderedProductRepository;
import com.crudshop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;

    @Override
    public UUID createOrder(final UUID customerId, final List<OrderedProductInfo> productIds) {
        final CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException
                        ("Пользователь с таким ID " + customerId + " не был найден "));

        final OrderEntity order = OrderEntity.builder()
                .customer(customer)
                .totalPrice(0.0)
                .status(OrderStatus.CREATED)
                .build();

        for (OrderedProductInfo orderedProductInfo : productIds) {
            final ProductEntity product = productRepository.findById(orderedProductInfo.getId())
                    .orElseThrow(() -> new ProductNotFoundException
                            ("Продукт с таким ID  " + orderedProductInfo.getId() + " не был найден"));

            int orderedQuantity = orderedProductInfo.getQuantity();
            if (product.getQuantity() < orderedQuantity) {
                throw new NotEnoughQuantityForProductException
                        ("Недостаточное количество продуктов с ID" + product.getId());
            }
            product.setQuantity(product.getQuantity() - orderedQuantity);
            order.setTotalPrice(order.getTotalPrice() + (product.getPrice() * orderedQuantity));

            final OrderedProductKey orderedProductKey = new OrderedProductKey();
            orderedProductKey.setOrderId(order.getId());
            orderedProductKey.setProductId(product.getId());
            final OrderedProduct orderedProduct = OrderedProduct.builder()
                    .orderedProductKey(orderedProductKey)
                    .order(order)
                    .product(product)
                    .quantity(orderedQuantity)
                    .price(product.getPrice() * orderedQuantity)
                    .build();
            orderRepository.save(order);
            orderedProductRepository.save(orderedProduct);
        }
        return order.getId();
    }

    @Override
    public OrderDto updateStatusOnOrder(final UUID orderId, final OrderDto orderDto) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с таким id не был найден "));
        order.setStatus(orderDto.getStatus());

        final OrderEntity updatedOrder = orderRepository.save(order);

        return OrderDto.builder()
                .id(updatedOrder.getId())
                .customer(updatedOrder.getCustomer())
                .totalPrice(updatedOrder.getTotalPrice())
                .status(updatedOrder.getStatus())
                .build();
    }
}
