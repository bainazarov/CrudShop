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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "Контроллер для продуктов")
public interface ProductController {

    @PostMapping
    @Operation(summary = "Регистрация продукта")
    UUID createProduct(@RequestBody @Valid final CreateProductRequest createProductRequest);

    @GetMapping("/{id}")
    @Operation(summary = "Получить продукт по ID")
    GetProductResponse getProductById(@PathVariable final UUID id);

    @PutMapping("/{id}")
    @Operation(summary = "Обновить продукт по ID")
    UUID updateProduct(@PathVariable final UUID id, @RequestBody @Valid final UpdateProductRequest request);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить продукт по ID")
    ResponseEntity<Void> deleteProduct(@PathVariable final UUID id);

    @GetMapping
    @Operation(summary = "Получить все продукты")
    List<GetProductResponse> findAll(@RequestParam(defaultValue = "0") final int page,
                                     @RequestParam(defaultValue = "10") final int size);

    @GetMapping("/search")
    List<GetProductResponse> searchProducts(@RequestParam(required = false) final String name,
                                            @RequestParam(required = false) final Integer quantity,
                                            @RequestParam(required = false) final Double price,
                                            @RequestParam(required = false) final Boolean isAvailable);
}
