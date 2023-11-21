package com.crudshop.demo.configuration;

import com.crudshop.demo.conversion.ProductDtoToEntityConvert;
import com.crudshop.demo.conversion.ProductEntityToDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConversionConfig {

    private final ProductEntityToDtoConverter productEntityToDtoConverter;
    private final ProductDtoToEntityConvert productDtoToEntityConvert;

    public ConversionConfig(ProductEntityToDtoConverter productEntityToDtoConverter, ProductDtoToEntityConvert productDtoToEntityConvert) {
        this.productEntityToDtoConverter = productEntityToDtoConverter;
        this.productDtoToEntityConvert = productDtoToEntityConvert;
    }

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(productEntityToDtoConverter);
        conversionService.addConverter(productDtoToEntityConvert);
        return conversionService;
    }
}
