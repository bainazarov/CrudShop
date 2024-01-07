package com.crudshop.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    private BigDecimal exchangeRateRUB;
    private BigDecimal exchangeRateUSD;
    private BigDecimal exchangeRateEUR;

}
