package com.crudshop.demo.conversion;
import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.dto.ProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CreatedRequestToProductDto implements Converter<CreateProductRequest, ProductDto> {
    @Override
    public ProductDto convert(CreateProductRequest source) {
        return ProductDto.builder()
                .name(source.getName())
                .description(source.getDescription())
                .categories(source.getCategories())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .build();
    }
}
