package com.crudshop.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductFilterDto {

    private String name;
    private Double price;
    private Integer quantity;
    private Boolean isAvailable;
}
