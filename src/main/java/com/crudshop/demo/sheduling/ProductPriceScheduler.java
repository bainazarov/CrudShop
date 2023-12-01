package com.crudshop.demo.sheduling;

import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ConditionalOnProperty(name = "app.scheduling.enabled")
public class ProductPriceScheduler {

    private final ProductRepository productRepository;
    private final Double priceIncreasePercentage;

    public ProductPriceScheduler(ProductRepository productRepository,
                                 @Value("${app.scheduling.priceIncreasePercentage:10}") Double priceIncreasePercentage) {
        this.productRepository = productRepository;
        this.priceIncreasePercentage = priceIncreasePercentage;
    }

    @Scheduled(fixedRateString = "${app.scheduling.period}")
    public void increaseProductPrice() throws InterruptedException {
        List<ProductEntity> products = productRepository.findAll();

        products.forEach(product -> {
            double currentPrice = product.getPrice();
            double newPrice = currentPrice * (1 + (priceIncreasePercentage / 100));
            product.setPrice(newPrice);
        });

        productRepository.saveAll(products);
    }
}