package com.crudshop.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class OrderedProductKey implements Serializable {

    private UUID orderId;

    private UUID productId;
}
