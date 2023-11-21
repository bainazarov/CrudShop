package com.crudshop.demo.dto;

import com.crudshop.demo.entity.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProductDto {

    @NotBlank(message = "Поле не может быть пустым")
    private String name;

    private String description;

    private Categories categories;

    @NotNull(message = "Поле не может быть пустым")
    private double price;

    @NotNull(message = "Поле не может быть пустым")
    private Integer quantity;
}
