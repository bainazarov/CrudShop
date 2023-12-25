package com.crudshop.demo.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CustomerDto {

    UUID id;

    String name;

    String email;
}
