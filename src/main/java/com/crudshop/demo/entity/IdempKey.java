package com.crudshop.demo.entity;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("idempotencykeys")
public class IdempKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String key;

    private UUID orderId;

}