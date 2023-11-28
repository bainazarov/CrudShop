package com.crudshop.demo.service;

import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.entity.Categories;
import com.crudshop.demo.entity.ProductEntity;
import com.crudshop.demo.exception.ArticleAlreadyExistsException;
import com.crudshop.demo.exception.ProductNotFoundException;
import com.crudshop.demo.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void createProductTest_WhenExistingProduct_ThrowsArticleAlreadyExistsException() {
        String article = "12345";
        ProductDto productDto = ProductDto.builder()
                .article(article)
                .build();

        ProductEntity product = new ProductEntity();
        product.setId(UUID.randomUUID());
        product.setArticle(article);

        Mockito.when(productRepository.findByArticle(article)).thenReturn(Optional.of(product));

        Assertions.assertThrows(ArticleAlreadyExistsException.class, () -> productService.createProduct(productDto));
    }

    @Test
    public void createProductTest_WhenNewProduct_ReturnsProductDto() {
        String article = "666666";
        String name = "Груша";
        String description = "Спелая";
        Categories categories = Categories.FRUIT;
        double price = 22.00;
        Integer quantity = 25;

        ProductDto productDto = ProductDto.builder()
                .article(article)
                .name(name)
                .description(description)
                .categories(categories)
                .price(price)
                .quantity(quantity)
                .build();

        Mockito.when(productRepository.findByArticle(article)).thenReturn(Optional.empty());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID());
        productEntity.setArticle(article);
        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setCategories(categories);
        productEntity.setPrice(price);
        productEntity.setQuantity(quantity);

        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);

        UUID productId = productService.createProduct(productDto);

        Assertions.assertNotNull(productId);
        Assertions.assertEquals(article, productEntity.getArticle());
        Assertions.assertEquals(name, productEntity.getName());
        Assertions.assertEquals(description, productEntity.getDescription());
        Assertions.assertEquals(categories, productEntity.getCategories());
        Assertions.assertEquals(price, productEntity.getPrice());
        Assertions.assertEquals(quantity, productEntity.getQuantity());
    }

    @Test
    public void getProductByIdTest_WhenProductExists_ReturnsProductDto() {
        UUID productId = UUID.randomUUID();
        String article = "666666";
        String name = "Яблоко";
        String description = "Красная спелая";
        Categories categories = Categories.FRUIT;
        double price = 50.00;
        Integer quantity = 25;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setArticle(article);
        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setCategories(categories);
        productEntity.setPrice(price);
        productEntity.setQuantity(quantity);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        ProductDto productDto = productService.getProductById(productId);

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(productId, productDto.getId());
        Assertions.assertEquals(article, productDto.getArticle());
        Assertions.assertEquals(name, productDto.getName());
        Assertions.assertEquals(description, productDto.getDescription());
        Assertions.assertEquals(categories, productDto.getCategories());
        Assertions.assertEquals(price, productDto.getPrice());
        Assertions.assertEquals(quantity, productDto.getQuantity());


    }

    @Test
    public void getProductByIdTest_WhenProductDoesNotExist_ThrowsProductNotFoundException() {
        UUID productId = UUID.randomUUID();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    public void updateProductTest_WhenProductExists_ReturnsProductId() {
        UUID productId = UUID.randomUUID();
        String article = "123456";
        String name = "Манго";
        String description = "Спелый";
        Categories categories = Categories.FRUIT;
        double price = 20.00;
        int quantity = 15;

        ProductDto productDto = ProductDto.builder()
                .article(article)
                .name(name)
                .description(description)
                .categories(categories)
                .price(price)
                .quantity(quantity)
                .build();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setArticle("654321");
        productEntity.setName("Слива");
        productEntity.setDescription("Зеленый");
        productEntity.setCategories(Categories.VEGETABLES);
        productEntity.setPrice(25.00);
        productEntity.setQuantity(25);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        Mockito.when(productRepository.isArticleExists(article)).thenReturn(false);
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);

        UUID updatedProductId = productService.updateProduct(productId, productDto);

        Assertions.assertEquals(productId, updatedProductId);
        Assertions.assertEquals(article, productEntity.getArticle());
        Assertions.assertEquals(name, productEntity.getName());
        Assertions.assertEquals(description, productEntity.getDescription());
        Assertions.assertEquals(categories, productEntity.getCategories());
        Assertions.assertEquals(price, productEntity.getPrice());
        Assertions.assertEquals(quantity, productEntity.getQuantity());

    }

    @Test
    public void updateProductTest_WhenProductDoesNotExist_ThrowsProductNotFoundException() {
        UUID productId = UUID.randomUUID();
        ProductDto productDto = ProductDto.builder()
                .article("1234566")
                .name("Малина")
                .description("Красная")
                .categories(Categories.FRUIT)
                .price(55.00)
                .quantity(15)
                .build();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, productDto));
    }

    @Test
    public void updateProductTest_WhenArticleAlreadyExists_ThrowsArticleAlreadyExistsException() {
        UUID productId = UUID.randomUUID();
        String existingArticle = "555555";
        String newArticle = "666666";

        ProductDto productDto = ProductDto.builder()
                .article(newArticle)
                .name("Тыква")
                .description("Оранжевая")
                .categories(Categories.VEGETABLES)
                .price(60.00)
                .quantity(44)
                .build();

        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(productId);
        existingProduct.setArticle(existingArticle);
        existingProduct.setName("Тыква");
        existingProduct.setDescription("Оранжевая");
        existingProduct.setCategories(Categories.VEGETABLES);
        existingProduct.setPrice(60.00);
        existingProduct.setQuantity(44);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepository.isArticleExists(newArticle)).thenReturn(true);

        Assertions.assertThrows(ArticleAlreadyExistsException.class, () -> productService.updateProduct(productId, productDto));
    }

    @Test
    public void updateProductTest_WhenQuantityChanges_SetsLastQuantityChange() {
        UUID productId = UUID.randomUUID();
        String article = "123456";
        String name = "Банан";
        String description = "Желтый";
        Categories categories = Categories.FRUIT;
        double price = 50.0;
        int oldQuantity = 15;
        int newQuantity = 18;

        ProductDto productDto = ProductDto.builder()
                .article(article)
                .name(name)
                .description(description)
                .categories(categories)
                .price(price)
                .quantity(newQuantity)
                .build();

        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(productId);
        existingProduct.setArticle(article);
        existingProduct.setName(name);
        existingProduct.setDescription(description);
        existingProduct.setCategories(categories);
        existingProduct.setPrice(price);
        existingProduct.setQuantity(oldQuantity);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepository.isArticleExists(article)).thenReturn(false);
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(existingProduct);

        productService.updateProduct(productId, productDto);

        Assertions.assertNotNull(existingProduct.getLastQuantityChange());
        Assertions.assertNotEquals(existingProduct.getLastQuantityChange(), existingProduct.getCreatedAt());
    }

    @Test
    public void deleteProductTest_WhenProductExists_DeletesProduct() {
        UUID productId = UUID.randomUUID();

        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setArticle("666666");
        product.setName("Яблоко");
        product.setDescription("Красный");
        product.setCategories(Categories.FRUIT);
        product.setPrice(50.00);
        product.setQuantity(30);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(productId);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());
        Optional<ProductEntity> deletedProduct = productRepository.findById(productId);

        Assertions.assertTrue(deletedProduct.isEmpty());
    }

    @Test
    public void deleteProductTest_WhenProductDoesNotExist_ThrowsProductNotFoundException() {
        UUID productId = UUID.randomUUID();

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));

        Mockito.verify(productRepository, Mockito.never()).deleteById(productId);
    }

    @Test
    public void getAllProductsTest_ReturnsProductDtoPage() {
        Pageable pageable = Pageable.ofSize(2).withPage(0);

        ProductEntity product1 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .article("123456")
                .name("Яблоко")
                .description("Красное")
                .categories(Categories.FRUIT)
                .price(50.00)
                .quantity(25)
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .id(UUID.randomUUID())
                .article("12345678")
                .name("Банан")
                .description("Желтый")
                .categories(Categories.FRUIT)
                .price(55.00)
                .quantity(20)
                .build();

        List<ProductEntity> productEntities = Arrays.asList(product1, product2);
        Page<ProductEntity> productEntityPage = new PageImpl<>(productEntities, pageable, productEntities.size());

        Mockito.when(productRepository.findAll(pageable)).thenReturn(productEntityPage);

        Page<ProductDto> productDtoPage = productService.getAllProducts(pageable);

        Assertions.assertNotNull(productDtoPage);
        Assertions.assertEquals(productEntities.size(), productDtoPage.getSize());
    }
}