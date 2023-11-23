package com.crudshop.demo.controller.product;


import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.controller.product.response.GetProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ProductController {

    @PostMapping
    UUID createProduct(@RequestBody @javax.validation.Valid CreateProductRequest createProductRequest);

    @GetMapping("/{id}")
    GetProductResponse getProductById(@PathVariable UUID id);

    @PutMapping("/{id}")
    UUID updateProduct(@PathVariable UUID id, @RequestBody @Valid UpdateProductRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable UUID id);

    @GetMapping
    List<GetProductResponse> findAll();
}
