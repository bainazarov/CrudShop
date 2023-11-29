package com.crudshop.demo.service;

import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.entity.Categories;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.exception.ArticleAlreadyExistsException;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.motherObject.ProductDtoBuilder;
import com.crudshop.demo.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private ProductServiceImpl productServiceUT;

    @Test
    public void createProductTest_WhenExistingProduct_ThrowsArticleAlreadyExistsException() {
        String article = "12345";
        ProductDto productDto = ProductDtoBuilder.aProductDto()
                .withArticle(article)
                .build();

        ProductEntity product = new ProductEntity();
        product.setId(UUID.randomUUID());
        product.setArticle(article);

        when(productRepositoryMock.findByArticle(article)).thenReturn(Optional.of(product));

        assertThrows(ArticleAlreadyExistsException.class, () -> productServiceUT.createProduct(productDto),
                "Ожидалось ArticleAlreadyExistsException, но он не сработал ");

        verify(productRepositoryMock, times(0)).save(any());
    }

    @Test
    public void createProductTest_WhenNewProduct_ReturnsProductDto() {
        String expectedArticle = "Expected_article";
        ProductDto productDto = ProductDto.builder()
                .article(expectedArticle)
                .build();

        when(productRepositoryMock.findByArticle(any())).thenReturn(Optional.empty());

        UUID expected = UUID.randomUUID();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(expected);

        when(productRepositoryMock.save(any())).thenReturn(productEntity);

        UUID actual = productServiceUT.createProduct(productDto);
        assertEquals(expected, actual);

        verify(productRepositoryMock).findByArticle(expectedArticle);
    }

    @Test
    public void getProductByIdTest_WhenProductExists_ReturnsProductDto() {
        UUID expectedProductId = UUID.randomUUID();
        String expectedArticle = "666666";
        String expectedName = "Яблоко";
        String expectedDescription = "Красная спелая";
        Categories expectedCategories = Categories.FRUIT;
        double expectedPrice = 50.00;
        Integer expectedQuantity = 25;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(expectedProductId);
        productEntity.setArticle(expectedArticle);
        productEntity.setName(expectedName);
        productEntity.setDescription(expectedDescription);
        productEntity.setCategories(expectedCategories);
        productEntity.setPrice(expectedPrice);
        productEntity.setQuantity(expectedQuantity);

        when(productRepositoryMock.findById(expectedProductId)).thenReturn(Optional.of(productEntity));

        ProductDto actual = productServiceUT.getProductById(expectedProductId);

        assertNotNull(actual);
        assertEquals(expectedProductId, actual.getId());
        assertEquals(expectedArticle, actual.getArticle());
        assertEquals(expectedName, actual.getName());
        assertEquals(expectedDescription, actual.getDescription());
        assertEquals(expectedCategories, actual.getCategories());
        assertEquals(expectedPrice, actual.getPrice());
        assertEquals(expectedQuantity, actual.getQuantity());
    }

    @Test
    public void getProductByIdTest_WhenProductDoesNotExist_ThrowsProductNotFoundException() {
        when(productRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productServiceUT.getProductById(UUID.randomUUID()),
                "Ожидалось ProductNotFoundException, но он не сработал");
    }

    @Test
    public void updateProductTest_WhenProductExists_ReturnsProductId() {
        UUID expectedProductId = UUID.randomUUID();
        String expectedArticle = "123456";
        String expectedName = "Манго";
        String expectedDescription = "Спелый";
        Categories expectedCategories = Categories.FRUIT;
        double expectedPrice = 20.00;
        int expectedQuantity = 15;

        ProductDto productDto = ProductDto.builder()
                .article(expectedArticle)
                .name(expectedName)
                .description(expectedDescription)
                .categories(expectedCategories)
                .price(expectedPrice)
                .quantity(expectedQuantity)
                .build();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(expectedProductId);
        productEntity.setArticle("654321");
        productEntity.setName("Слива");
        productEntity.setDescription("Зеленый");
        productEntity.setCategories(Categories.VEGETABLES);
        productEntity.setPrice(25.00);
        productEntity.setQuantity(25);

        when(productRepositoryMock.findById(expectedProductId)).thenReturn(Optional.of(productEntity));
        when(productRepositoryMock.isArticleExists(expectedArticle)).thenReturn(false);

        ArgumentCaptor<ProductEntity> productEntityCaptor = ArgumentCaptor.forClass(ProductEntity.class);
        when(productRepositoryMock.save(productEntityCaptor.capture())).thenReturn(productEntity);

        UUID updatedProductId = productServiceUT.updateProduct(expectedProductId, productDto);

        assertEquals(expectedProductId, updatedProductId);

        ProductEntity capturedProductEntity = productEntityCaptor.getValue();
        assertEquals(expectedArticle, capturedProductEntity.getArticle());
        assertEquals(expectedName, capturedProductEntity.getName());
        assertEquals(expectedDescription, capturedProductEntity.getDescription());
        assertEquals(expectedCategories, capturedProductEntity.getCategories());
        assertEquals(expectedPrice, capturedProductEntity.getPrice());
        assertEquals(expectedQuantity, capturedProductEntity.getQuantity());
    }

    @Test
    public void updateProductTest_WhenProductDoesNotExist_ThrowsProductNotFoundException() {
        when(productRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productServiceUT.updateProduct(UUID.randomUUID(), ProductDto.builder().build()),
                "Ожидалось ProductNotFoundException, но он не сработал");
    }

    @Test
    public void updateProductTest_WhenArticleAlreadyExists_ThrowsArticleAlreadyExistsException() {
        UUID productId = UUID.randomUUID();
        String existingArticle = "555555";
        String newArticle = "666666";

        ProductDto productDto = ProductDto.builder()
                .article(newArticle)
                .build();

        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(productId);
        existingProduct.setArticle(existingArticle);

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepositoryMock.isArticleExists(newArticle)).thenReturn(true);

        assertThrows(ArticleAlreadyExistsException.class, () -> productServiceUT.updateProduct(productId, productDto),
                "Ожидалось ArticleAlreadyExistsException, но он не сработал");
    }

    @Test
    public void updateProductTest_WhenQuantityChanges_SetsLastQuantityChange() {
        UUID productId = UUID.randomUUID();
        String article = "555555";
        int oldQuantity = 15;
        int newQuantity = 18;

        ProductDto productDto = ProductDto.builder()
                .article(article)
                .quantity(newQuantity)
                .build();

        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(productId);
        existingProduct.setQuantity(oldQuantity);

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepositoryMock.isArticleExists(article)).thenReturn(false);
        when(productRepositoryMock.save(Mockito.any(ProductEntity.class))).thenReturn(existingProduct);

        productServiceUT.updateProduct(productId, productDto);

        assertNotNull(existingProduct.getLastQuantityChange());
        Assertions.assertNotEquals(existingProduct.getLastQuantityChange(), existingProduct.getCreatedAt());
    }

    @Test
    public void deleteProductTest_WhenProductExists_DeletesProduct() {
        UUID productId = UUID.randomUUID();

        ProductEntity product = new ProductEntity();
        product.setId(productId);

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(product));

        productServiceUT.deleteProduct(productId);

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());
        Optional<ProductEntity> deletedProduct = productRepositoryMock.findById(productId);

        Assertions.assertTrue(deletedProduct.isEmpty());
    }

    @Test
    public void deleteProductTest_WhenProductDoesNotExist_ThrowsProductNotFoundException() {
        UUID productId = UUID.randomUUID();

        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productServiceUT.deleteProduct(productId),
                "Ожидалось ProductNotFoundException, но он не сработал");

        Mockito.verify(productRepositoryMock, Mockito.never()).deleteById(productId);
    }

    @Test
    public void getAllProductsTest_ReturnsProductDtoPage() {
        Pageable pageable = Pageable.ofSize(2).withPage(0);

        ProductEntity product1 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .build();

        List<ProductEntity> productEntities = Arrays.asList(product1, product2);
        Page<ProductEntity> productEntityPage = new PageImpl<>(productEntities, pageable, productEntities.size());

        when(productRepositoryMock.findAll(pageable)).thenReturn(productEntityPage);

        Page<ProductDto> productDtoPage = productServiceUT.getAllProducts(pageable);

        assertNotNull(productDtoPage);
        assertEquals(productEntities.size(), productDtoPage.getSize());
    }
}