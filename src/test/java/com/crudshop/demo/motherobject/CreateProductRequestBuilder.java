package com.crudshop.demo.motherobject;

import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.entity.Categories;

import java.math.BigDecimal;

import static com.crudshop.demo.entity.Categories.FRUIT;

public class CreateProductRequestBuilder {
    public static final String DEFAULT_ARTICLE = "1234567";
    public static final String DEFAULT_NAME = "Яблоко";
    public static final String DEFAULT_DESCRIPTION = "Круглое красное";
    public static final Categories DEFAULT_CATEGORIES = FRUIT;
    public static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(50.00);
    public static final Integer DEFAULT_QUANTITY = 15;
    public static final Boolean DEFAULT_IS_AVAILABLE = true;


    private String article = DEFAULT_ARTICLE;
    private String name = DEFAULT_NAME;
    private String description = DEFAULT_DESCRIPTION;
    private Categories categories = DEFAULT_CATEGORIES;
    private BigDecimal price = DEFAULT_PRICE;
    private Integer quantity = DEFAULT_QUANTITY;
    private Boolean isAvailable = DEFAULT_IS_AVAILABLE;

    private CreateProductRequestBuilder() {
    }

    public static CreateProductRequestBuilder aProductDto() {
        return new CreateProductRequestBuilder();
    }


    public CreateProductRequestBuilder withArticle(String article) {
        this.article = article;
        return this;
    }

    public CreateProductRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateProductRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateProductRequestBuilder withCategories(Categories categories) {
        this.categories = categories;
        return this;
    }

    public CreateProductRequestBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CreateProductRequestBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public CreateProductRequestBuilder withIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public CreateProductRequest build() {
        return CreateProductRequest.builder()
                .article(article)
                .name(name)
                .description(description)
                .categories(categories)
                .price(price)
                .quantity(quantity)
                .isAvailable(isAvailable)
                .build();
    }

}