package com.crudshop.demo.controller.product;

import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.controller.product.response.GetProductResponse;
import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.dto.ProductFilterDto;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    private final ConversionService conversionService;

    @Override
    public UUID createProduct(final CreateProductRequest request) {
        final ProductDto productDto = conversionService.convert(request, ProductDto.class);
        log.info("Создали продукт с id " + productDto.getId());


        return productService.createProduct(productDto);
    }

    @Override
    public GetProductResponse getProductById(final UUID id) {
        final ProductDto productDto = productService.getProductById(id);
        log.info("Получили продукт с id " + id);

        return conversionService.convert(productDto, GetProductResponse.class);
    }

    @Override
    public UUID updateProduct(final UUID id, final UpdateProductRequest request) {
        log.info("Обновили продукт с id " + id);

        return productService.updateProduct(id, conversionService.convert(request, ProductDto.class));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(final UUID id) {
        try {
            productService.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        log.info("Удалили продукт с id " + id);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<GetProductResponse> findAll(int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        final Page<ProductDto> productPage = productService.getAllProducts(pageable);
        log.info("Получили лист продуктов страница " + page + " количество" + size);

        return productPage.getContent().stream()
                .map(product -> conversionService.convert(product, GetProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetProductResponse> searchProducts(@RequestParam(required = false) final String name,
                                                   @RequestParam(required = false) final Integer quantity,
                                                   @RequestParam(required = false) final Double price,
                                                   @RequestParam(required = false) final Boolean isAvailable) {
        final ProductFilterDto filter = ProductFilterDto.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .isAvailable(isAvailable)
                .build();
        final List<ProductDto> products = productService.searchProducts(filter);

        productService.saveProductsToXlsx(products);
        log.info("Отфильтровали продукты по данным параметрам " + filter);

        return products.stream()
                .map(productDto -> conversionService.convert(productDto, GetProductResponse.class))
                .collect(Collectors.toList());
    }
}
