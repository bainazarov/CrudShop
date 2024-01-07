package com.crudshop.demo.sheduling;

import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Component
@ConditionalOnProperty(name = "app.scheduling.enabled")
public class ProductPriceScheduler {

    private final ProductRepository productRepository;
    private final BigDecimal priceIncreasePercentage;

    public ProductPriceScheduler(ProductRepository productRepository,
                                 @Value("${app.scheduling.priceIncreasePercentage:10}") BigDecimal priceIncreasePercentage) {
        this.productRepository = productRepository;
        this.priceIncreasePercentage = priceIncreasePercentage;
    }

    @Scheduled(fixedRateString = "${app.scheduling.period}")
    @Transactional
    public void increaseProductPrice() throws InterruptedException {
        List<ProductEntity> products = productRepository.findAll();

        products.forEach(product -> {
            BigDecimal currentPrice = product.getPrice();
            BigDecimal newPrice = currentPrice.multiply(BigDecimal.ONE.add
                    (priceIncreasePercentage.divide(BigDecimal.valueOf(100))));
            product.setPrice(newPrice.setScale(2, RoundingMode.HALF_UP));
        });
        Thread.sleep(30000);
        productRepository.saveAll(products);
    }
}