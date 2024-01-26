package com.crudshop.demo.entity.projection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductProjection {

    @Id
    private UUID productId;
    private String description;
    private Integer quantity;
    private BigDecimal totalPrice;
}
