package com.crudshop.demo.service.order;

import com.crudshop.demo.controller.order.request.OrderedProductInfo;
import com.crudshop.demo.controller.order.response.GetOrderAndProductIDResponse;
import com.crudshop.demo.dto.CustomerDto;
import com.crudshop.demo.dto.OrderDto;
import com.crudshop.demo.dto.OrderWithCustomerDto;
import com.crudshop.demo.entity.CustomerEntity;
import com.crudshop.demo.entity.OrderEntity;
import com.crudshop.demo.entity.OrderStatus;
import com.crudshop.demo.entity.OrderedProduct;
import com.crudshop.demo.entity.OrderedProductKey;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.entity.projection.ProductProjection;
import com.crudshop.demo.exception.CustomerNotFoundException;
import com.crudshop.demo.exception.NotEnoughQuantityForProductException;
import com.crudshop.demo.exception.OrderNotFoundException;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.interaction.GetINNClient;
import com.crudshop.demo.repository.CustomerRepository;
import com.crudshop.demo.repository.OrderRepository;
import com.crudshop.demo.repository.OrderedProductRepository;
import com.crudshop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final GetINNClient getINNClient;


    @Override
    @Transactional
    public UUID createOrder(final UUID customerId, final List<OrderedProductInfo> products) {
        final CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException
                        ("Пользователь с таким ID " + customerId + " не был найден "));

        final OrderEntity order = OrderEntity.builder()
                .customer(customer)
                .totalPrice(0.0)
                .status(OrderStatus.CREATED)
                .build();

        final List<UUID> productIds = products.stream()
                .map(OrderedProductInfo::getId)
                .collect(Collectors.toList());

        final List<ProductEntity> productList = productRepository.findAllById(productIds);
        Map<UUID, ProductEntity> productMap = productList.stream()
                .collect(Collectors.toMap(ProductEntity::getId, product -> product));

        final List<OrderedProduct> orderedProducts = products.stream()
                .map(p -> {
                    final UUID productId = p.getId();
                    int orderedQuantity = p.getQuantity();

                    final ProductEntity product = productMap.get(productId);
                    if (product == null) {
                        throw new ProductNotFoundException("Продукт с ID " + productId + " не был найден");
                    }

                    if (product.getQuantity() < orderedQuantity) {
                        throw new NotEnoughQuantityForProductException
                                ("Недостаточное количество продуктов с ID " + productId);
                    }

                    double productPrice = product.getPrice() * orderedQuantity;

                    product.setQuantity(product.getQuantity() - orderedQuantity);

                    final OrderedProductKey orderedProductKey = new OrderedProductKey();
                    orderedProductKey.setOrderId(order.getId());
                    orderedProductKey.setProductId(productId);

                    return OrderedProduct.builder()
                            .orderedProductKey(orderedProductKey)
                            .order(order)
                            .product(product)
                            .quantity(orderedQuantity)
                            .price(productPrice)
                            .build();
                })
                .collect(Collectors.toList());

        order.setTotalPrice(
                orderedProducts.stream()
                        .mapToDouble(x -> x.getQuantity() * x.getPrice())
                        .sum()
        );
        productRepository.saveAll(productList);
        orderRepository.save(order);
        orderedProductRepository.saveAll(orderedProducts);

        return order.getId();
    }

    @Override
    public List<ProductProjection> getOrderById(final UUID orderId, final UUID customerId) {
        return orderRepository.getProjectionsByOrderIdAndCustomerId(orderId, customerId);
    }

    @Override
    @Transactional
    public UUID addProductsToExistingOrder(final UUID orderId, final List<OrderedProductInfo> products) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с таким ID " + orderId + " не был найден"));
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Невозможно добавить продукты к заказу со статусом " + order.getStatus());
        }

        final List<OrderedProduct> orderedProducts = new ArrayList<>();
        final Map<UUID, ProductEntity> productMap = productRepository.findAllById(products.stream()
                        .map(OrderedProductInfo::getId)
                        .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(ProductEntity::getId, product -> product));

        for (OrderedProductInfo orderedProductInfo : products) {
            final UUID productId = orderedProductInfo.getId();
            int orderedQuantity = orderedProductInfo.getQuantity();
            final ProductEntity product = productMap.get(productId);
            if (product == null) {
                throw new ProductNotFoundException("Продукт с ID " + productId + " не был найден");
            }
            if (product.getQuantity() < orderedQuantity) {
                throw new NotEnoughQuantityForProductException("Недостаточное количество продуктов с ID " + productId);
            }
            double productPrice = product.getPrice() * orderedQuantity;
            OrderedProduct orderedProduct = orderedProductRepository.findByOrderAndProduct(order, product);
            if (orderedProduct != null) {
                orderedProduct.setQuantity(orderedProduct.getQuantity() + orderedQuantity);
                orderedProduct.setPrice(orderedProduct.getPrice() + productPrice);
            } else {
                final OrderedProductKey orderedProductKey = new OrderedProductKey();
                orderedProductKey.setOrderId(orderId);
                orderedProductKey.setProductId(productId);
                orderedProduct = OrderedProduct.builder()
                        .orderedProductKey(orderedProductKey)
                        .order(order)
                        .product(product)
                        .quantity(orderedQuantity)
                        .price(productPrice)
                        .build();
            }
            orderedProducts.add(orderedProduct);
            product.setQuantity(product.getQuantity() - orderedQuantity);
        }
        order.setTotalPrice(
                orderedProducts.stream()
                        .mapToDouble(x -> x.getQuantity() * x.getPrice())
                        .sum()
        );
        productRepository.saveAll(productMap.values());
        orderRepository.save(order);
        orderedProductRepository.saveAll(orderedProducts);
        return orderId;
    }


    @Override
    public OrderDto updateStatusOnOrder(final UUID orderId, final OrderStatus status) {
        final OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с таким id не был найден "));
        order.setStatus(status);

        final OrderEntity updatedOrder = orderRepository.save(order);

        return OrderDto.builder()
                .id(updatedOrder.getId())
                .customer(updatedOrder.getCustomer())
                .totalPrice(updatedOrder.getTotalPrice())
                .status(updatedOrder.getStatus())
                .build();
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID orderId) {
        List<OrderedProduct> orderedProducts = orderedProductRepository.findAll()
                .stream()
                .filter(orderedProduct -> orderedProduct.getOrderedProductKey().getOrderId().equals(orderId))
                .collect(Collectors.toList());

        for (OrderedProduct orderedProduct : orderedProducts) {
            ProductEntity product = orderedProduct.getProduct();
            product.setQuantity(product.getQuantity() + orderedProduct.getQuantity());
        }

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Заказ с таким id не был найден"));
        order.setStatus(OrderStatus.CANCELLED);

        orderedProductRepository.saveAll(orderedProducts);
        orderRepository.save(order);
    }

    @Override
    public GetOrderAndProductIDResponse getOrdersByProductId() {
        final List<OrderedProduct> orderedProducts = orderedProductRepository.findAll();
        final List<UUID> orderIds = orderedProducts.stream()
                .map(orderedProduct -> orderedProduct.getOrderedProductKey().getOrderId())
                .collect(Collectors.toList());

        final List<OrderEntity> orders = orderRepository.findAllById(orderIds);
        final List<CustomerEntity> customers = customerRepository.findAll();
        final List<String> emails = new ArrayList<>();

        for (CustomerEntity customer : customers) {
            emails.add(customer.getEmail());
        }
        final Map<String, String> emailToINNMap = getINNClient.getINN(emails);

        final Map<UUID, List<OrderWithCustomerDto>> ordersGroupedByProductId = orderedProducts.stream()
                .filter(orderedProduct -> orderedProduct.getOrderedProductKey().getProductId() != null)
                .collect(Collectors.groupingBy(orderedProduct -> orderedProduct.getOrderedProductKey().getProductId(),
                        Collectors.mapping(orderedProduct -> {
                            final OrderEntity order = orders.stream()
                                    .filter(ordered ->
                                            ordered.getId().equals(orderedProduct.getOrderedProductKey().getOrderId()))
                                    .findFirst()
                                    .orElse(null);
                            if (order != null) {
                                final CustomerEntity customer = order.getCustomer();
                                return OrderWithCustomerDto.builder()
                                        .id(order.getId())
                                        .customer(CustomerDto.builder()
                                                .id(customer.getId())
                                                .name(customer.getName())
                                                .email(customer.getEmail())
                                                .inn(emailToINNMap.get(customer.getEmail()))
                                                .build())
                                        .totalPrice(order.getTotalPrice())
                                        .status(order.getStatus())
                                        .build();
                            } else {
                                return null;
                            }
                        }, Collectors.toList())));

        return GetOrderAndProductIDResponse.builder()
                .orders(ordersGroupedByProductId)
                .build();
    }
}
