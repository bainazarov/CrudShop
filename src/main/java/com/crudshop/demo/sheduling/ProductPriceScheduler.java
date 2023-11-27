package com.crudshop.demo.sheduling;

import com.crudshop.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ProductPriceScheduler {

    private final ProductRepository productRepository;
    private final Boolean isEnabled;
    private final Double priceIncreasePercentage;

    public ProductPriceScheduler(ProductRepository productRepository,
                                 @Value("${app.scheduling.enabled:false}") Boolean isEnabled,
                                 @Value("${app.scheduling.priceIncreasePercentage:10}") Double priceIncreasePercentage) {
        this.productRepository = productRepository;
        this.isEnabled = isEnabled;
        this.priceIncreasePercentage = priceIncreasePercentage;
    }

    @Scheduled(fixedRateString = "${app.scheduling.period}")
    public void increaseProductPrice() {

        if (!isEnabled) {
            return;
        }

        productRepository.findAll().stream()
                .peek(product -> {
                    double currentPrice = product.getPrice();
                    double newPrice = currentPrice * (1 + (priceIncreasePercentage / 100));
                    product.setPrice(newPrice);
                })
                .forEach(productRepository::save);
    }
}