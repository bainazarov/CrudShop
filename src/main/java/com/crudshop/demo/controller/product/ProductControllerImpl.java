package com.crudshop.demo.controller.product;

import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.controller.product.response.GetProductResponse;
import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    private final ConversionService conversionService;

    @Override
    public UUID createProduct(CreateProductRequest request) {
        ProductDto productDto = conversionService.convert(request, ProductDto.class);

        return productService.createProduct(productDto);
    }

    @Override
    public GetProductResponse getProductById(UUID id) {
        ProductDto productDto = productService.getProductById(id);

        return conversionService.convert(productDto, GetProductResponse.class);
    }

    @Override
    public UUID updateProduct(UUID id, UpdateProductRequest request) {
        return productService.updateProduct(id, conversionService.convert(request, ProductDto.class));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(UUID id) {
        try {
            productService.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public List<GetProductResponse> findAll() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return productDtoList.stream()
                .map(product -> conversionService.convert(product, GetProductResponse.class))
                .collect(Collectors.toList());
    }

}
