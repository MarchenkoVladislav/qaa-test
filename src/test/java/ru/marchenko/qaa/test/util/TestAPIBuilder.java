package ru.marchenko.qaa.test.util;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

/**
 * Interface for build REST API tests
 *
 * @author Created by Vladislav Marchenko on 10.03.2021
 */
public interface TestAPIBuilder {
    void buildTestWithoutParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification
    );

    void buildTestWithPathParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            Map<String, Object> pathParams
    );

    void buildTestWithParams(
            RequestSpecification requestSpecification,
            ResponseSpecification responseSpecification,
            Map<String, Object> params
    );
}
