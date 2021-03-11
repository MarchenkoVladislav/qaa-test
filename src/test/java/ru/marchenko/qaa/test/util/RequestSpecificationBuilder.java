package ru.marchenko.qaa.test.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Class that builds requests specifications
 *
 * @author Created by Vladislav Marchenko on 10.03.2021
 */
public class RequestSpecificationBuilder {

    /**
     * Method that build request specification
     *
     * @param basePath base path for request specification
     * @return request specification
     */
    public RequestSpecification build(
            String baseUri,
            ContentType contentType,
            String basePath
    ) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(contentType)
                .setBasePath(basePath)
                .build();
    }
}
