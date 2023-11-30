package com.crudshop.demo.controller;


import com.crudshop.demo.controller.product.request.CreateProductRequest;
import com.crudshop.demo.controller.product.request.UpdateProductRequest;
import com.crudshop.demo.motherobject.CreateProductRequestBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @LocalServerPort
    private Integer port;


    @Test
    public void createProductTest_ShouldReturn200() {
        String requestCreate = "{\"article\": \"3333333\", \"name\":\"Яблоко\", \"description\":\"Красное\"," +
                " \"categories\":\"FRUIT\", \"price\":\"25.00\", \"quantity\":\"20\"}";

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestCreate)
                .when()
                .post("http://localhost:" + port + "/my-app/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(is(notNullValue()));
    }

    @Test
    public void createProductTest_ShouldReturn400() {
        CreateProductRequestBuilder createProductRequestBuilder = CreateProductRequestBuilder.aProductDto();
        CreateProductRequest requestCreate = createProductRequestBuilder
                .withArticle("")
                .withName("")
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestCreate)
                .when()
                .post("http://localhost:" + port + "/my-app/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getProductByIdTest_ShouldReturn200() {
        given().port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:" + port + "/my-app/products/{id}", "304303e1-e585-42a3-8a6c-105301b7406f")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getProductByIdTest_ShouldReturn400() {
        given().port(port)
                .when()
                .get("http://localhost:" + port + "/my-app/products/{id}", "132123231123")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void updateProductTest_ShouldReturn200() {
        String requestUpdate = "{\"name\":\"Груша\", \"description\":\"Желтая\"," +
                " \"categories\":\"FRUIT\", \"price\":\"55.00\", \"quantity\":\"5\"}";

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestUpdate)
                .when()
                .put("http://localhost:" + port + "/my-app/products/{id}", "304303e1-e585-42a3-8a6c-105301b7406f")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateProductTest_ShouldReturn400() {
        UpdateProductRequest requestUpdate = UpdateProductRequest.builder()
                .name("")
                .description("")
                .build();

        given().port(port)
                .contentType(ContentType.JSON)
                .body(requestUpdate)
                .when()
                .put("http://localhost:" + port + "/my-app/products/{id}", "304303e1-e585-42a3-8a6c-105301b7406f")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deleteProductTest_ShouldReturn204() {
        UUID productId = UUID.randomUUID();

        given().port(port)
                .when()
                .delete("http://localhost:" + port + "/my-app/products/{id}", productId.toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
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
        given().port(port)
                .when()
                .get("http://localhost:" + port + "/my-app/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThan(0));
    }
}
