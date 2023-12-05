package com.crudshop.demo.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Component
public class ExchangeRateProvider {
    public double getExchangeRate() {
        double exchangeRate = 0.0;
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new FileReader
                    ("src/main/resources/exchange-rate.json"), JsonObject.class);
            exchangeRate = jsonObject.get("exchangeRate").getAsDouble();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при парсинге файла");
        }
        return exchangeRate;
    }
}