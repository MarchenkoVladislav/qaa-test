package ru.marchenko.qaa.test.util;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Class that builds REST API tests for GET requests
 *
 * @author Created by Vladislav Marchenko on 10.03.2021
 */
public class TestAPIBuilder {

    /**
     * Method that builds tests for GET requests without params
     * @param requestSpecification specification for request
     * @param responseSpecification specification for response
     */
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


    /**
     * Method that builds tests for GET requests with path params
     * @param requestSpecification specification for request
     * @param responseSpecification specification for response
     * @param pathParams path params for request
     */
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


    /**
     * Method that builds tests for GET requests with params
     * @param requestSpecification specification for request
     * @param responseSpecification specification for response
     * @param params params for request
     */
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
