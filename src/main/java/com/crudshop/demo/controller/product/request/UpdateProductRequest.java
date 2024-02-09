package com.crudshop.demo.controller.product.request;

import com.crudshop.demo.entity.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class UpdateProductRequest {

    private String article;

    @NotBlank(message = "Name can not be blank")
    private String name;

    private String description;

    private Categories categories;

    @Positive(message = "Price should be at least 0 or higher")
    private BigDecimal price;

    @Positive(message = "Quantity can not be negative")
    private Integer quantity;

    @NotNull(message = "isAvailable can not be blank")
    private Boolean isAvailable;

}
