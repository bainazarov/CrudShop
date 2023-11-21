package com.crudshop.demo.conversion;

import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToEntityConvert implements Converter<ProductDto, ProductEntity> {
    @Override
    public ProductEntity convert(ProductDto source) {
        return ProductEntity.builder()
                .name(source.getName())
                .description(source.getDescription())
                .categories(source.getCategories())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .build();
    }
}
