package com.crudshop.demo.interaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetINNClientImpl implements GetINNClient {

    private final RestTemplate exchangeRateRestTemplate;

    @Override
    public Map<String, String> getINN(final List<String> emails) {
        try {
            log.info("Делаем вызов ИНН второго сервиса");
            final String url = "/INN?emails=" + String.join(",", emails);

            return exchangeRateRestTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            log.error("Ошибка получения ИНН из второго сервиса: " + e.getMessage());
            return null;
        }
    }


}
