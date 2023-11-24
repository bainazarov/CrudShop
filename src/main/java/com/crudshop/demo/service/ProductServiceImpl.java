package com.crudshop.demo.service;

import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public UUID createProduct(final ProductDto productDto) {
        final ProductEntity entity = ProductEntity.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .categories(productDto.getCategories())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        return productRepository.save(entity).getId();
    }

    @Override
    public ProductDto getProductById(UUID id) {
        final ProductEntity productDto = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден "));

        return ProductDto.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .categories(productDto.getCategories())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .lastQuantityChange(productDto.getLastQuantityChange())
                .createdAt(productDto.getCreatedAt())
                .build();

    }

    @Override
    public UUID updateProduct(UUID id, final ProductDto productDto) {
        final ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден "));

        if (!Objects.equals(product.getQuantity(), productDto.getQuantity())) {
            product.setLastQuantityChange(LocalDateTime.now());
        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategories(productDto.getCategories());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        return productRepository.save(product).getId();
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден "));

        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(entity -> ProductDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .categories(entity.getCategories())
                        .price(entity.getPrice())
                        .quantity(entity.getQuantity())
                        .lastQuantityChange(entity.getLastQuantityChange())
                        .createdAt(entity.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
