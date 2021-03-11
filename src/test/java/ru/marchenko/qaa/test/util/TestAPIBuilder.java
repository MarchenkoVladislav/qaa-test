package ru.marchenko.qaa.test.util;

import io.restassured.response.ValidatableResponse;
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
     *
     * @param requestSpecification  specification for request
     * @param responseSpecification specification for response
     * @return validatable response
     */
    public ValidatableResponse buildTestWithoutParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification
    ) {
        return given()
                .spec(requestSpecification)
                .when()
                .get()
                .then()
                .log().body()
                .spec(responseSpecification);
    }


    /**
     * Method that builds tests for GET requests with path params
     *
     * @param requestSpecification  specification for request
     * @param responseSpecification specification for response
     * @param pathParams            path params for request
     * @return validatable response
     */
    public ValidatableResponse buildTestWithPathParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            Map<String, Object> pathParams
    ) {
        return given()
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
     *
     * @param requestSpecification  specification for request
     * @param responseSpecification specification for response
     * @param params                params for request
     * @return validatable response
     */
    public ValidatableResponse buildTestWithParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            Map<String, Object> params
    ) {
        return given()
                .spec(requestSpecification)
                .params(params)
                .when()
                .get()
                .then()
                .log().body()
                .spec(responseSpecification);
    }
}
