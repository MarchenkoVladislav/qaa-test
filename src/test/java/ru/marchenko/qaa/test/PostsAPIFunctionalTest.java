package ru.marchenko.qaa.test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.marchenko.qaa.test.util.TestAPIBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

/**
 * @author Created by Vladislav Marchenko on 08.03.2021
 */
public class PostsAPIFunctionalTest {

    /**
     * Path to JSON schema that validates responses with one post
     */
    private final static String PATH_TO_SCHEMA_FOR_ONE_POST = "JSONSchemaForOnePost.json";

    /**
     * Path to JSON schema that validates responses with many posts
     */
    private final static String PATH_TO_SCHEMA_FOR_MANY_POSTS = "JSONSchemaForManyPosts.json";

    /**
     * Name of "id" field
     */
    private final static String ID_FIELD_NAME = "id";

    /**
     * Name of "userId" field
     */
    private final static String USER_ID_FIELD_NAME = "userId";

    /**
     * Name of "title" field
     */
    private final static String TITLE_FIELD_NAME = "title";

    /**
     * Name of "body" field
     */
    private final static String BODY_FIELD_NAME = "body";

    /**
     * Valid id value
     */
    private final static int VALID_ID = 1;

    /**
     * Invalid id value (number)
     */
    private final static int INVALID_ID_NUMBER = -1;

    /**
     * Invalid id value (not number)
     */
    private final static String INVALID_ID_NOT_NUMBER = "b";

    /**
     * Valid userId value
     */
    private final static int VALID_USER_ID = 1;

    /**
     * Invalid userId value (number)
     */
    private final static int INVALID_USER_ID_NUMBER = -1;

    /**
     * Invalid userId value (not number)
     */
    private final static String INVALID_USER_ID_NOT_NUMBER = "b";

    /**
     * Valid title value
     */
    private final static String VALID_TITLE = "voluptatem eligendi optio";

    /**
     * Valid body value
     */
    private final static String VALID_BODY = "fuga et accusamus dolorum perferendis illo voluptas" +
            "\\nnon doloremque neque facere\\nad qui dolorum molestiae beatae\\nsed aut voluptas totam sit illum";

    /**
     * Invalid title value
     */
    private final static int INVALID_TITLE = 1;

    /**
     * Invalid body value
     */
    private final static int INVALID_BODY = 1;

    /**
     * Valid response status
     */
    private final static int VALID_STATUS = 200;

    /**
     * Base URI
     */
    private final static String BASE_URI = "https://jsonplaceholder.typicode.com";

    /**
     * Content type
     */
    private final static ContentType CONTENT_TYPE = ContentType.JSON;

    /**
     * Base path for requests that get posts by id
     */
    private final static String BASE_PATH_POST_BY_ID = "/posts/{id}";

    /**
     * Base path for requests that get all posts
     */
    private final static String BASE_PATH_ALL_POSTS = "/posts";

    /**
     * Base path for requests that get posts by user id
     */
    private final static String BASE_PATH_POST_BY_USER_ID = "/posts?userId={userId}";

    /**
     * Base path for requests that get posts by title
     */
    private final static String BASE_PATH_POST_BY_TITLE = "/posts?title={title}";

    /**
     * Base path for requests that get posts by body (field)
     */
    private final static String BASE_PATH_POST_BY_BODY = "/posts?body={body}";

    /**
     * Empty JSON brackets
     */
    private final static String EMPTY_JSON = "{}";

    /**
     * Empty array brackets
     */
    private final static String EMPTY_ARRAY = "[]";

    /**
     * Specification for requests which get posts by id
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(CONTENT_TYPE)
            .setBasePath(BASE_PATH_POST_BY_ID)
            .build();

    /**
     * Specification for requests which get all posts
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_ALL = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(CONTENT_TYPE)
            .setBasePath(BASE_PATH_ALL_POSTS)
            .build();

    /**
     * Specification for requests which get posts by userId
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(CONTENT_TYPE)
            .setBasePath(BASE_PATH_POST_BY_USER_ID)
            .build();

    /**
     * Specification for requests which get posts by title
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_TITLE = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(CONTENT_TYPE)
            .setBasePath(BASE_PATH_POST_BY_TITLE)
            .build();

    /**
     * Specification for requests which get posts by body (field)
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_BODY = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(CONTENT_TYPE)
            .setBasePath(BASE_PATH_POST_BY_BODY)
            .build();

    /**
     * Specification for responses from requests which get posts by valid id
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID = new ResponseSpecBuilder()
            .expectBody(ID_FIELD_NAME, anyOf(equalTo(VALID_ID), nullValue()))
            .expectBody(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_ONE_POST), is(EMPTY_JSON)))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get posts by invalid id
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_ID = new ResponseSpecBuilder()
            .expectBody(ID_FIELD_NAME, nullValue())
            .expectBody(is(EMPTY_JSON))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get all posts
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_ALL = new ResponseSpecBuilder()
            .expectBody(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_JSON)))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get posts by valid userId
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID = new ResponseSpecBuilder()
            .expectBody(USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue()))
            .expectBody(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_JSON)))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get posts by invalid userId
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_USER_ID = new ResponseSpecBuilder()
            .expectBody(USER_ID_FIELD_NAME, everyItem(nullValue()))
            .expectBody(is(EMPTY_ARRAY))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get posts by valid title
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_TITLE = new ResponseSpecBuilder()
            .expectBody(TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue()))
            .expectBody(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY)))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get posts by invalid title
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_TITLE = new ResponseSpecBuilder()
            .expectBody(TITLE_FIELD_NAME, everyItem(nullValue()))
            .expectBody(is(EMPTY_ARRAY))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get posts by valid body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_BODY = new ResponseSpecBuilder()
            .expectBody(BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue()))
            .expectBody(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY)))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Specification for responses from requests which get posts by invalid body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_BODY = new ResponseSpecBuilder()
            .expectBody(BODY_FIELD_NAME, everyItem(nullValue()))
            .expectBody(is(EMPTY_ARRAY))
            .expectStatusCode(VALID_STATUS)
            .build();

    /**
     * Params for requests which get posts by id (valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID = new HashMap<>();

    /**
     * Params for requests which get posts by id (invalid number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_ID_NUMBER = new HashMap<>();

    /**
     * Params for requests which get posts by id (invalid not number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_ID_NOT_NUMBER = new HashMap<>();

    /**
     * Params for requests which get posts by userId (valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_USER_ID = new HashMap<>();

    /**
     * Params for requests which get posts by userId (invalid number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_USER_ID_NUMBER = new HashMap<>();

    /**
     * Params for requests which get posts by userId (invalid not number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_USER_ID_NOT_NUMBER = new HashMap<>();

    /**
     * Params for requests which get posts by title (valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_TITLE = new HashMap<>();

    /**
     * Params for requests which get posts by title (invalid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_TITLE = new HashMap<>();

    /**
     * Params for requests which get posts by body (field; valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_BODY = new HashMap<>();

    /**
     * Params for requests which get posts by body (field; invalid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_BODY = new HashMap<>();

    /**
     * Builder for tests
     */
    private final TestAPIBuilder testAPIBuilder = new TestAPIBuilder();

    @BeforeAll
    public static void setUp() {
        PARAMS_FOR_GET_BY_VALID_ID.put(ID_FIELD_NAME, VALID_ID);
        PARAMS_FOR_GET_BY_INVALID_ID_NUMBER.put(ID_FIELD_NAME, INVALID_ID_NUMBER);
        PARAMS_FOR_GET_BY_INVALID_ID_NOT_NUMBER.put(ID_FIELD_NAME, INVALID_ID_NOT_NUMBER);

        PARAMS_FOR_GET_BY_VALID_USER_ID.put(USER_ID_FIELD_NAME, VALID_USER_ID);
        PARAMS_FOR_GET_BY_INVALID_USER_ID_NUMBER.put(USER_ID_FIELD_NAME, INVALID_USER_ID_NUMBER);
        PARAMS_FOR_GET_BY_INVALID_USER_ID_NOT_NUMBER.put(USER_ID_FIELD_NAME, INVALID_USER_ID_NOT_NUMBER);

        PARAMS_FOR_GET_BY_VALID_TITLE.put(TITLE_FIELD_NAME, VALID_TITLE);
        PARAMS_FOR_GET_BY_INVALID_TITLE.put(TITLE_FIELD_NAME, INVALID_TITLE);

        PARAMS_FOR_GET_BY_VALID_BODY.put(BODY_FIELD_NAME, VALID_BODY);
        PARAMS_FOR_GET_BY_INVALID_BODY.put(BODY_FIELD_NAME, INVALID_BODY);
    }

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    public void testGetPostByValidId() {
        testAPIBuilder.buildTestWithPathParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID,
                PARAMS_FOR_GET_BY_VALID_ID
        );
    }

    @Test
    public void testGetPostByInvalidIdWhichIsNumber() {
        testAPIBuilder.buildTestWithPathParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID,
                RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_ID,
                PARAMS_FOR_GET_BY_INVALID_ID_NUMBER
        );
    }

    @Test
    public void testGetPostByInvalidIdWhichIsNotNumber() {
        testAPIBuilder.buildTestWithPathParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID,
                RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_ID,
                PARAMS_FOR_GET_BY_INVALID_ID_NOT_NUMBER
        );
    }

    @Test
    public void testGetAllPosts() {
        testAPIBuilder.buildTestWithoutParams(
                REQUEST_SPECIFICATION_FOR_GET_ALL,
                RESPONSE_SPECIFICATION_FOR_GET_ALL
        );
    }

    @Test
    public void testGetPostsByValidUserId() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID,
                PARAMS_FOR_GET_BY_VALID_USER_ID
        );
    }

    @Test
    public void testGetPostsByInvalidUserIdWhichIsNumber() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID,
                RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_USER_ID,
                PARAMS_FOR_GET_BY_INVALID_USER_ID_NUMBER
        );
    }

    @Test
    public void testGetPostsByInvalidUserIdWhichIsNotNumber() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID,
                RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_USER_ID,
                PARAMS_FOR_GET_BY_INVALID_USER_ID_NOT_NUMBER
        );
    }

    @Test
    public void testGetPostByValidTitle() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_TITLE,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_TITLE,
                PARAMS_FOR_GET_BY_VALID_TITLE
        );
    }

    @Test
    public void testGetPostByInvalidTitle() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_TITLE,
                RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_TITLE,
                PARAMS_FOR_GET_BY_INVALID_TITLE
        );
    }

    @Test
    public void testGetPostByValidBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_BODY,
                PARAMS_FOR_GET_BY_VALID_BODY
        );
    }

    @Test
    public void testGetPostByInvalidBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_BODY,
                PARAMS_FOR_GET_BY_INVALID_BODY
        );
    }

}
