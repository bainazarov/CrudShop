package com.crudshop.demo.service.product;

import com.crudshop.demo.annotation.MeasureExecutionTime;
import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.dto.ProductFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ProductService {

    UUID createProduct(ProductDto productDto);

    ProductDto getProductById(UUID id);

    UUID updateProduct(UUID id, ProductDto productDto);

    void deleteProduct(UUID id);

    Page<ProductDto> getAllProducts(Pageable pageable);

    List<ProductDto> searchProducts(ProductFilterDto filter);

    void saveProductsToXlsx(List<ProductDto> products);
}
