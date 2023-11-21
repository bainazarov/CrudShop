package com.crudshop.demo.service;
import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ConversionService conversionService) {
        this.productRepository = productRepository;
        this.conversionService = conversionService;
    }


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        ProductEntity product = conversionService.convert(productDto, ProductEntity.class);
        product.setCreatedDate(LocalDateTime.now());
        product.setLastQuantityChange(LocalDateTime.now());
        ProductEntity saveProduct = productRepository.save(product);
        return conversionService.convert(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(UUID id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Продукт с таким id не был найден " + id));
        return conversionService.convert(product, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(UUID id, ProductDto productDto) {
        ProductEntity product = conversionService.convert(productDto, ProductEntity.class);
        product.setId(id);
        product.setLastQuantityChange(LocalDateTime.now());
        ProductEntity updateProduct = productRepository.save(product);
        return conversionService.convert(updateProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
