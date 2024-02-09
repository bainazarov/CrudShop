package com.crudshop.demo.repository;

import com.crudshop.demo.entity.OrderEntity;
import com.crudshop.demo.entity.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findOrdersByCustomerId(UUID customerId);

    @Query("""
                select new ProductProjection (
                    op.orderedProductKey.productId,
                    pe.description,
                    op.quantity,
                    op.price
                  )
                from OrderedProduct op
                  join ProductEntity pe on pe.id = op.orderedProductKey.productId
                  join OrderEntity oe on oe.id = op.orderedProductKey.orderId
                where op.orderedProductKey.orderId = :orderId
                and oe.customer.id = :customerId
            """)
    List<ProductProjection> getProjectionsByOrderIdAndCustomerId
            (UUID orderId, UUID customerId);
}
