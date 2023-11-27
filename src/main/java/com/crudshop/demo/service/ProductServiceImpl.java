package com.crudshop.demo.service;

import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.exception.ArticleAlreadyExistsException;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public UUID createProduct(final ProductDto productDto) {
        final Optional<ProductEntity> existingProduct = productRepository.findByArticle(productDto.getArticle());
        if (existingProduct.isPresent()) {
            throw new ArticleAlreadyExistsException("Продукт с таким артикулом уже существует");
        }
        final ProductEntity entity = ProductEntity.builder()
                .article(productDto.getArticle())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .categories(productDto.getCategories())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        return productRepository.save(entity).getId();
    }

    @Override
    public ProductDto getProductById(final UUID id) {
        final ProductEntity productDto = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден "));

        return ProductDto.builder()
                .id(productDto.getId())
                .article(productDto.getArticle())
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
    public UUID updateProduct(final UUID id, final ProductDto productDto) {
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
    public void deleteProduct(final UUID id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден "));

        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(entity -> ProductDto.builder()
                        .id(entity.getId())
                        .article(entity.getArticle())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .categories(entity.getCategories())
                        .price(entity.getPrice())
                        .quantity(entity.getQuantity())
                        .lastQuantityChange(entity.getLastQuantityChange())
                        .createdAt(entity.getCreatedAt())
                        .build());
    }
}
