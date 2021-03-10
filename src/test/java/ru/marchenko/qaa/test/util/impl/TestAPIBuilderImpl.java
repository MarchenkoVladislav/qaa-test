package ru.marchenko.qaa.test.util.impl;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import ru.marchenko.qaa.test.util.TestAPIBuilder;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @author Created by Vladislav Marchenko on 10.03.2021
 */
public class TestAPIBuilderImpl implements TestAPIBuilder {
    @Override
    public void buildTestWithoutParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification
    ) {
        given()
                .spec(requestSpecification)
                .when()
                .get()
                .then()
                .log().body()
                .spec(responseSpecification);
    }

    @Override
    public void buildTestWithPathParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            Map<String, Object> pathParams
    ) {
        given()
                .spec(requestSpecification)
                .pathParams(pathParams)
                .when()
                .get()
                .then()
                .log().body()
                .spec(responseSpecification);
    }

    @Override
    public void buildTestWithParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            Map<String, Object> params
    ) {
        given()
                .spec(requestSpecification)
                .params(params)
                .when()
                .get()
                .then()
                .log().body()
                .spec(responseSpecification);
    }
}
