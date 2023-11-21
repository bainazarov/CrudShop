package com.crudshop.demo.service;

import com.crudshop.demo.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(UUID id);

    ProductDto updateProduct(UUID id, ProductDto productDto);

    void deleteProduct(UUID id);
}
