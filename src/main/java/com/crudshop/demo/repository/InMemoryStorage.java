package com.crudshop.demo.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class InMemoryStorage {

    private Map<String, UUID> idempKeyMap = new HashMap<>();

    public void saveIdempKey(String key, UUID orderId) {
        idempKeyMap.put(key, orderId);
    }

    public UUID findOrderIdByKey(String key) {
        return idempKeyMap.get(key);
    }
}
