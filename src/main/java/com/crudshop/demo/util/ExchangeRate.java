package com.crudshop.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    private Double exchangeRateRUB;
    private Double exchangeRateUSD;
    private Double exchangeRateEUR;

}
