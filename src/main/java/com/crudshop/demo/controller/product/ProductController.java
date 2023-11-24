package com.crudshop.demo.controller.product;


import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.controller.product.response.GetProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Контроллер для продуктов")
public interface ProductController {

    @PostMapping
    @Operation(summary = "Регистрация продукта")
    UUID createProduct(@RequestBody @Valid CreateProductRequest createProductRequest);

    @GetMapping("/{id}")
    @Operation(summary = "Полученить продукт по ID")
    GetProductResponse getProductById(@PathVariable UUID id);

    @PutMapping("/{id}")
    @Operation(summary = "Обновление продукта по ID")
    UUID updateProduct(@PathVariable UUID id, @RequestBody @Valid UpdateProductRequest request);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить продукт по ID")
    ResponseEntity<Void> deleteProduct(@PathVariable UUID id);

    @GetMapping
    @Operation(summary = "Получить все продукты")
    List<GetProductResponse> findAll();
}
