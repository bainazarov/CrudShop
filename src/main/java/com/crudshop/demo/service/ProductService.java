package com.crudshop.demo.service;

import com.crudshop.demo.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ProductService {

    UUID createProduct(ProductDto productDto);

    ProductDto getProductById(UUID id);

    UUID updateProduct(UUID id, ProductDto productDto);

    void deleteProduct(UUID id);

    List<ProductDto> getAllProducts();
}
