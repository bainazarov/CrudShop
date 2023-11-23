package com.crudshop.demo.controller.product.request;

import com.crudshop.demo.entity.Categories;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class CreateProductRequest {

    private String article;

    @NotBlank(message = "Name can not be null")
    private String name;

    private String description;

    private Categories categories;

    @Positive(message = "Price should be at least 0 or higher")
    private double price;

    @NotNull(message = "Quantity can not be null")
    private Integer quantity;
}
