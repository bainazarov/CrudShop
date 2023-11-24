package com.crudshop.demo.dto;

import com.crudshop.demo.entity.Categories;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class ProductDto {

    UUID id;

    @Nullable
    String name;

    @Nullable
    String description;

    @Nullable
    Categories categories;

    double price;

    Integer quantity;

    LocalDateTime lastQuantityChange;

    LocalDateTime createdAt;
}
