package com.crudshop.demo.controller.product.request;

import com.crudshop.demo.entity.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

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
    private Double price;

    @NotNull(message = "Quantity can not be null")
    private Integer quantity;

    private Boolean isAvailable;
}
