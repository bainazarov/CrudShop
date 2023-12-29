package com.crudshop.demo.repository;

import com.crudshop.demo.entity.OrderEntity;
import com.crudshop.demo.entity.OrderedProduct;
import com.crudshop.demo.entity.OrderedProductKey;
import com.crudshop.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedProductKey> {
    OrderedProduct findByOrderAndProduct(OrderEntity order, ProductEntity product);
}
