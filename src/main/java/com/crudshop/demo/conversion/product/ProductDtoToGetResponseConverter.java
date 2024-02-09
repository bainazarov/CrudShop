package com.crudshop.demo.conversion.product;

import com.crudshop.demo.controller.product.response.GetProductResponse;
import com.crudshop.demo.dto.ProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToGetResponseConverter implements Converter<ProductDto, GetProductResponse> {
    @Override
    public GetProductResponse convert(ProductDto source) {
        return GetProductResponse.builder()
                .id(source.getId())
                .article(source.getArticle())
                .name(source.getName())
                .description(source.getDescription())
                .categories(source.getCategories())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .isAvailable(source.getIsAvailable())
                .lastQuantityChange(source.getLastQuantityChange())
                .createdAt(source.getCreatedAt())
                .build();
    }
}
