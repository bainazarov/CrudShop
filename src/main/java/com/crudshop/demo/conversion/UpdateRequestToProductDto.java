package com.crudshop.demo.conversion;

import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.dto.ProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateRequestToProductDto implements Converter<UpdateProductRequest, ProductDto> {
    @Override
    public ProductDto convert(UpdateProductRequest source) {
        return ProductDto.builder()
                .name(source.getName())
                .description(source.getDescription())
                .categories(source.getCategories())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .build();
    }
}
