package com.crudshop.demo.repository;

import com.crudshop.demo.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findByArticle(String article);

    @Query("select case when (count(p) > 0) then true else false end from ProductEntity p where p.article = :article")
    boolean isArticleExists(String article);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<ProductEntity> findAll();
}
