package com.crudshop.demo.util;

import com.crudshop.demo.controller.product.response.GetProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class CurrencyConverterAdvice implements ResponseBodyAdvice<GetProductResponse> {
    private final ExchangeRateProvider exchangeRateProvider;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String className = returnType.getContainingClass().toString();
        String methodName = returnType.getMethod().toString();

        return className.contains("ProductController") && methodName.contains("getProductById");
    }

    @Override
    public GetProductResponse beforeBodyWrite(GetProductResponse body, MethodParameter returnType,
                                              MediaType selectedContentType,
                                              Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                              ServerHttpRequest request, ServerHttpResponse response) {
        Double rate = exchangeRateProvider.getExchangeRate();
        Optional.ofNullable(body).ifPresent(b -> b.setPrice(body.getPrice() / rate));

        return body;
    }
}