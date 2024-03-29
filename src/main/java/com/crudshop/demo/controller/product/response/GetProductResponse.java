package com.crudshop.demo.controller.product.response;

import com.crudshop.demo.entity.Categories;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GetProductResponse {

    private UUID id;

    private String article;

    private String name;

    private String description;

    private Categories categories;

    private BigDecimal price;

    private Integer quantity;

    private Boolean isAvailable;

    private LocalDateTime lastQuantityChange;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy")
    private LocalDateTime createdAt;
}
