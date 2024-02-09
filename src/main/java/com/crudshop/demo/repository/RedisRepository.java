package com.crudshop.demo.repository;

import com.crudshop.demo.entity.IdempKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<IdempKey, String> {
}