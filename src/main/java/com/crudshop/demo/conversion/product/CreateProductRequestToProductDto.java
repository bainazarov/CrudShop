package com.crudshop.demo.conversion.product;
import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.dto.ProductDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CreateProductRequestToProductDto implements Converter<CreateProductRequest, ProductDto> {
    @Override
    public ProductDto convert(CreateProductRequest source) {
        return ProductDto.builder()
                .article(source.getArticle())
                .name(source.getName())
                .description(source.getDescription())
                .categories(source.getCategories())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .isAvailable(source.getIsAvailable())
                .build();
    }
}
