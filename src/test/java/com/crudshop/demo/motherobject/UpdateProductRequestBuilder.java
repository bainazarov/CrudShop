package com.crudshop.demo.motherobject;


import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.entity.Categories;

import static com.crudshop.demo.entity.Categories.FRUIT;

public class UpdateProductRequestBuilder {
    public static final String DEFAULT_ARTICLE = "1234567";
    public static final String DEFAULT_NAME = "Яблоко";
    public static final String DEFAULT_DESCRIPTION = "Круглое красное";
    public static final Categories DEFAULT_CATEGORIES = FRUIT;
    public static final Double DEFAULT_PRICE = 50.00;
    public static final Integer DEFAULT_QUANTITY = 15;
    public static final Boolean DEFAULT_IS_AVAILABLE = true;


    private String article = DEFAULT_ARTICLE;
    private String name = DEFAULT_NAME;
    private String description = DEFAULT_DESCRIPTION;
    private Categories categories = DEFAULT_CATEGORIES;
    private double price = DEFAULT_PRICE;
    private Integer quantity = DEFAULT_QUANTITY;
    private Boolean isAvailable = DEFAULT_IS_AVAILABLE;

    private UpdateProductRequestBuilder() {
    }

    public static UpdateProductRequestBuilder aProductDto() {
        return new UpdateProductRequestBuilder();
    }


    public UpdateProductRequestBuilder withArticle(String article) {
        this.article = article;
        return this;
    }

    public UpdateProductRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UpdateProductRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public UpdateProductRequestBuilder withCategories(Categories categories) {
        this.categories = categories;
        return this;
    }

    public UpdateProductRequestBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public UpdateProductRequestBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public UpdateProductRequestBuilder withIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public UpdateProductRequest build() {
        return UpdateProductRequest.builder()
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