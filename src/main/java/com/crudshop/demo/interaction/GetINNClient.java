package com.crudshop.demo.interaction;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface GetINNClient {

    Map<String, String> getINN(List<String> emails);
}
