package com.crudshop.demo.controller.product.request;

import com.crudshop.demo.entity.Categories;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;


@Data
@Builder
public class UpdateProductRequest {

    private String name;

    private String description;

    private Categories categories;

    @Positive(message = "Price should be at least 0 or higher")
    private double price;

    private Integer quantity;

}
