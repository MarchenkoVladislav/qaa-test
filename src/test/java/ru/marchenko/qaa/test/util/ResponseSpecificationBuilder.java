package ru.marchenko.qaa.test.util;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matcher;

import java.util.Map;

/**
 * Class that builds response specifications
 *
 * @author Created by Vladislav Marchenko on 10.03.2021
 */
public class ResponseSpecificationBuilder {

    /**
     * Method that builds response specifications for body check
     * (and others specifications with only one matcher)
     *
     * @param bodyMatcher matcher for body
     * @param status      expected request status
     * @return response specification
     */
    public ResponseSpecification buildSpecWithBodyCheck(
            Matcher<?> bodyMatcher,
            int status
    ) {
        return new ResponseSpecBuilder()
                .expectBody(bodyMatcher)
                .expectStatusCode(status)
                .build();
    }

    /**
     * Method that builds response specifications for body check
     * and all fields check
     *
     * @param fieldMatchers matchers for fields
     * @param bodyMatcher   matcher for body
     * @param status        expected request status
     * @return response specification
     */
    public ResponseSpecification buildSpecWithFieldsAndBodyCheck(
            Map<String, Matcher<?>> fieldMatchers,
            Matcher<?> bodyMatcher,
            int status
    ) {
        ResponseSpecBuilder responseSpecificationBuilder = new ResponseSpecBuilder();

        for (Map.Entry<String, Matcher<?>> elem : fieldMatchers.entrySet()) {
            responseSpecificationBuilder = responseSpecificationBuilder.expectBody(elem.getKey(), elem.getValue());
        }

        return responseSpecificationBuilder
                .expectBody(bodyMatcher)
                .expectStatusCode(status)
                .build();
    }
}
