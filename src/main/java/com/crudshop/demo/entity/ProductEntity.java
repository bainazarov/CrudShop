package com.crudshop.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "categories")
    private Categories categories;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "last_quantity_change")
    private LocalDateTime lastQuantityChange;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
