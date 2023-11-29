package com.crudshop.demo.motherObject;

import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.entity.Categories;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDtoBuilder {

    public static final UUID DEFAULT_ID = UUID.randomUUID();
    public static final String DEFAULT_ARTICLE = "1234567";
    public static final String DEFAULT_NAME = "Яблоко";
    public static final String DEFAULT_DESCRIPTION = "Круглое красное";
    public static final Categories DEFAULT_CATEGORIES = Categories.FRUIT;
    public static final double DEFAULT_PRICE = 50.00;
    public static final Integer DEFAULT_QUANTITY = 15;
    public static final LocalDateTime DEFAULT_LAST_QUANTITY_CHANGE = LocalDateTime.now();
    public static final LocalDateTime DEFAULT_CREATED_AT = LocalDateTime.now();


    private UUID id = DEFAULT_ID;
    private String article = DEFAULT_ARTICLE;
    private String name = DEFAULT_NAME;
    private String description = DEFAULT_DESCRIPTION;
    private Categories categories = DEFAULT_CATEGORIES;
    private double price = DEFAULT_PRICE;
    private Integer quantity = DEFAULT_QUANTITY;
    private LocalDateTime lastQuantityChange = DEFAULT_LAST_QUANTITY_CHANGE;
    private LocalDateTime createdAt = DEFAULT_CREATED_AT;

    private ProductDtoBuilder() {
    }

    public static ProductDtoBuilder aProductDto() {
        return new ProductDtoBuilder();
    }

    public ProductDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public ProductDtoBuilder withArticle(String article) {
        this.article = article;
        return this;
    }

    public ProductDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductDtoBuilder withUsername(String username) {
        this.article = article;
        return this;
    }

    public ProductDtoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductDtoBuilder withPrice() {
        this.price = price;
        return this;
    }

    public ProductDtoBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductDtoBuilder withLastQuantityChange() {
        this.lastQuantityChange = lastQuantityChange;
        return this;
    }

    public ProductDtoBuilder withCreatedAt() {
        this.createdAt = createdAt;
        return this;
    }

    public ProductDto build() {
        return ProductDto.builder()
                .id(id)
                .article(article)
                .name(name)
                .description(description)
                .categories(categories)
                .price(price)
                .quantity(quantity)
                .lastQuantityChange(lastQuantityChange)
                .createdAt(createdAt)
                .build();
    }

}
