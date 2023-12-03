package com.crudshop.demo.service;

import com.crudshop.demo.annotation.MeasureExecutionTime;
import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.dto.ProductFilterDto;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.exception.ArticleAlreadyExistsException;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @MeasureExecutionTime
    public UUID createProduct(final ProductDto productDto) {
        final Optional<ProductEntity> existingProduct = productRepository.findByArticle(productDto.getArticle());
        existingProduct.ifPresent(entity -> {
            throw new ArticleAlreadyExistsException("Продукт с таким артикулом уже существует", entity.getId());
        });
        final ProductEntity entity = ProductEntity.builder()
                .article(productDto.getArticle())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .categories(productDto.getCategories())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .isAvailable(Optional.ofNullable(productDto.getIsAvailable()).orElse(true))
                .build();

        return productRepository.save(entity).getId();
    }

    @Override
    @MeasureExecutionTime
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
                .isAvailable(productDto.getIsAvailable())
                .lastQuantityChange(productDto.getLastQuantityChange())
                .createdAt(productDto.getCreatedAt())
                .build();

    }

    @Override
    @MeasureExecutionTime
    public UUID updateProduct(final UUID id, final ProductDto productDto) {
        final ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден "));

        if (productRepository.isArticleExists(productDto.getArticle())) {
            throw new ArticleAlreadyExistsException("Продукт с таким артикулом уже существует");
        }

        if (!Objects.equals(product.getQuantity(), productDto.getQuantity())) {
            product.setLastQuantityChange(LocalDateTime.now());
        }

        product.setArticle(productDto.getArticle());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategories(productDto.getCategories());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setIsAvailable(productDto.getIsAvailable());

        return productRepository.save(product).getId();
    }

    @Override
    @MeasureExecutionTime
    public void deleteProduct(final UUID id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с таким id не был найден "));

        productRepository.deleteById(id);
    }

    @Override
    @MeasureExecutionTime
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
                        .isAvailable(entity.getIsAvailable())
                        .lastQuantityChange(entity.getLastQuantityChange())
                        .createdAt(entity.getCreatedAt())
                        .build());
    }

    @Override
    @MeasureExecutionTime
    public List<ProductDto> searchProducts(final ProductFilterDto filter) {
        final Specification<ProductEntity> specification = (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (filter.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
            }
            if (filter.getQuantity() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), filter.getQuantity()));
            }
            if (filter.getPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getPrice()));
            }
            if (filter.getIsAvailable() != null) {
                predicates.add(criteriaBuilder.equal(root.get("isAvailable"), filter.getIsAvailable()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        final List<ProductEntity> products = productRepository.findAll(specification);

        return products.stream()
                .map(productEntity -> ProductDto.builder()
                        .id(productEntity.getId())
                        .article(productEntity.getArticle())
                        .name(productEntity.getName())
                        .description(productEntity.getDescription())
                        .categories(productEntity.getCategories())
                        .price(productEntity.getPrice())
                        .quantity(productEntity.getQuantity())
                        .isAvailable(productEntity.getIsAvailable())
                        .lastQuantityChange(productEntity.getLastQuantityChange())
                        .createdAt(productEntity.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
