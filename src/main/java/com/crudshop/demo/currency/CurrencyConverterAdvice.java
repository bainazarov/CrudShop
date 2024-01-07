package com.crudshop.demo.currency;

import com.crudshop.demo.controller.product.response.GetProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class CurrencyConverterAdvice implements ResponseBodyAdvice<GetProductResponse> {
    private final ExchangeRateProvider exchangeRateProvider;
    private final CurrencyProvider currencyProvider;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String className = returnType.getContainingClass().toString();
        String methodName = returnType.getMethod().toString();

        return className.contains("ProductController") && methodName.contains("getProductById");
    }

    public GetProductResponse beforeBodyWrite(GetProductResponse body, MethodParameter returnType,
                                              MediaType selectedContentType,
                                              Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                              ServerHttpRequest request, ServerHttpResponse response) {
        Currency currency = currencyProvider.getCurrency();
        BigDecimal rate = exchangeRateProvider.getExchangeRate(currency);
        Optional.ofNullable(body).ifPresent(b -> b.setPrice(body.getPrice().divide(rate, RoundingMode.HALF_UP)));
        return body;
    }
}