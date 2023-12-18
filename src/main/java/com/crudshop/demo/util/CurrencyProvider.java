package com.crudshop.demo.util;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Data
public class CurrencyProvider {
    private Currency currency;

    public CurrencyProvider () {
        this.currency = Currency.RUB;
    }
}
