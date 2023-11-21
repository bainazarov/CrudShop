package com.crudshop.demo.controller;

import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto saveProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(saveProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        ProductDto getProduct = productService.getProductById(id);
        return ResponseEntity.ok(getProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductDto productDto) {
        ProductDto updatedProductDto = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProductDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
