package com.crudshop.demo.controller.product.request;

import com.crudshop.demo.entity.Categories;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class CreateProductRequest {

    @NotBlank(message = "Article can not be blank")
    private String article;

    @NotBlank(message = "Name can not be blank")
    private String name;

    private String description;

    @NotNull(message = "Categories can not be null")
    private Categories categories;

    @Positive(message = "Price should be at least 0 or higher")
    private BigDecimal price;

    @NotNull(message = "Quantity can not be null")
    @Min(value = 0L, message = "Quantity can not be negative")
    private Integer quantity;

    private Boolean isAvailable;
}
