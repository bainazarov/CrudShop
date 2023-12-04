package com.crudshop.demo.controller;


import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.dto.ProductDto;
import com.crudshop.demo.motherobject.CreateProductRequestBuilder;
import com.crudshop.demo.motherobject.ProductDtoBuilder;
import com.crudshop.demo.motherobject.UpdateProductRequestBuilder;
import com.crudshop.demo.service.product.ProductService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @LocalServerPort
    private Integer port;

    @MockBean
    private ProductService productServiceMock;


    @Test
    public void createProductTest_ShouldReturn200() {
        CreateProductRequest requestStub = CreateProductRequestBuilder.aProductDto().build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestStub)
                .when()
                .post("http://localhost:" + port + "/my-app/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(is(notNullValue()));
    }

    @Test
    public void createProductTest_ShouldReturn400() {
        CreateProductRequest requestStub = CreateProductRequestBuilder.aProductDto()
                .withArticle("")
                .withName("")
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestStub)
                .when()
                .post("http://localhost:" + port + "/my-app/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getProductByIdTest_ShouldReturn200() {
        when(productServiceMock.getProductById(Mockito.any())).thenReturn(ProductDtoBuilder.aProductDto().build());
        given().port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:" + port + "/my-app/products/{id}", UUID.randomUUID().toString())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getProductByIdTest_ShouldReturn400() {
        given().port(port)
                .when()
                .get("http://localhost:" + port + "/my-app/products/{id}", "332112312312323321123")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void updateProductTest_ShouldReturn200() {
        UpdateProductRequest requestStub = UpdateProductRequestBuilder.aProductDto()
                .withName("Груша")
                .withDescription("Большая")
                .build();
        when(productServiceMock.getProductById(Mockito.any())).thenReturn(ProductDtoBuilder.aProductDto().build());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestStub)
                .when()
                .put("http://localhost:" + port + "/my-app/products/{id}", UUID.randomUUID().toString())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateProductTest_ShouldReturn400() {
        UpdateProductRequest requestStub = UpdateProductRequestBuilder.aProductDto()
                .withName("")
                .withDescription("")
                .build();

        when(productServiceMock.getProductById(Mockito.any())).thenReturn(ProductDtoBuilder.aProductDto().build());

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestStub)
                .when()
                .put("http://localhost:" + port + "/my-app/products/{id}", UUID.randomUUID().toString())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deleteProductTest_ShouldReturn200() {
        given().port(port)
                .when()
                .delete("http://localhost:" + port + "/my-app/products/{id}", UUID.randomUUID().toString())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deleteProductTest_ShouldReturn400() {
        given().port(port)
                .when()
                .delete("http://localhost:" + port + "/my-app/products/{id}", "312231231321123321")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void findAllTest_ShouldReturnListOfProducts() {
        ProductDto product1 = ProductDtoBuilder.aProductDto().build();
        ProductDto product2 = ProductDtoBuilder.aProductDto().build();

        Page<ProductDto> mockProductPage = new PageImpl<>(List.of(product1, product2));
        when(productServiceMock.getAllProducts(Mockito.any())).thenReturn(mockProductPage);

        given().port(port)
                .when()
                .get("http://localhost:" + port + "/my-app/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThan(0));
    }
}
