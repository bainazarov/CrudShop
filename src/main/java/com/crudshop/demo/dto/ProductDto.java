package com.crudshop.demo.dto;

import com.crudshop.demo.entity.Categories;
import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Value
@Builder
public class ProductDto {

    UUID id;

    String article;

    String name;

    @Nullable
    String description;

    @Nullable
    Categories categories;

    BigDecimal price;

    Integer quantity;

    Boolean isAvailable;

    LocalDateTime lastQuantityChange;

    LocalDateTime createdAt;
}
