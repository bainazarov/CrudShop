package com.crudshop.demo.repository;

import com.crudshop.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByArticle(String article);

    @Query("select case when (count(p) > 0) then true else false end from ProductEntity p where p.article = :article")
    boolean isArticleExists(String article);
}
