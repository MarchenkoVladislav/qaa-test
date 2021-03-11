package ru.marchenko.qaa.test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.marchenko.qaa.test.util.RequestSpecificationBuilder;
import ru.marchenko.qaa.test.util.ResponseSpecificationBuilder;
import ru.marchenko.qaa.test.util.TestAPIBuilder;

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
    private final static String VALID_TITLE = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";

    /**
     * Valid body value
     */
    private final static String VALID_BODY = "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum" +
            "\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto";

    /**
     * Invalid title value
     */
    private final static int INVALID_TITLE = 1;

    /**
     * Invalid body value
     */
    private final static int INVALID_BODY = 1;

    /**
     * Empty JSON brackets
     */
    private final static String EMPTY_JSON = "{}";

    /**
     * Empty array brackets
     */
    private final static String EMPTY_ARRAY = "[]";

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
     * * Base path for requests that get post by id
     */
    private final static String BASE_PATH_POST_BY_ID = "/posts/{id}";

    /**
     * Base path for requests that get all posts
     */
    private final static String BASE_PATH_ALL_POSTS = "/posts";

    /**
     * Base path for requests that get posts by userId
     */
    private final static String BASE_PATH_POSTS_BY_USER_ID = "/posts?userId={userId}";

    /**
     * Base path for requests that get posts by title
     */
    private final static String BASE_PATH_POSTS_BY_TITLE = "/posts?title={title}";

    /**
     * Base path for requests that get posts by body (field)
     */
    private final static String BASE_PATH_POSTS_BY_BODY = "/posts?body={body}";

    /**
     * Base path for requests that get post by id and userId
     */
    private final static String BASE_PATH_POST_BY_ID_AND_USER_ID = "/posts?id={id}&userId={userId}";

    /**
     * Base path for requests that get post by id and title
     */
    private final static String BASE_PATH_POST_BY_ID_AND_TITLE = "/posts?id={id}&title={title}";

    /**
     * Base path for requests that get post by id and body
     */
    private final static String BASE_PATH_POST_BY_ID_AND_BODY = "/posts?id={id}&body={body}";

    /**
     * Base path for requests that get posts by userId and title
     */
    private final static String BASE_PATH_POSTS_BY_USER_ID_AND_TITLE = "/posts?userId={userId}&title={title}";

    /**
     * Base path for requests that get posts by userId and body
     */
    private final static String BASE_PATH_POSTS_BY_USER_ID_AND_BODY = "/posts?userId={userId}&body={body}";

    /**
     * Base path for requests that get posts by title and body
     */
    private final static String BASE_PATH_POSTS_BY_TITLE_AND_BODY = "/posts?title={title}&body={body}";

    /**
     * Base path for requests that get post by id, userId and title
     */
    private final static String BASE_PATH_POST_BY_ID_AND_USER_ID_AND_TITLE = "/posts?id={id}&userId={userId}&title={title}";

    /**
     * Base path for requests that get post by id, userId and body
     */
    private final static String BASE_PATH_POST_BY_ID_AND_USER_ID_AND_BODY = "/posts?id={id}&userId={userId}&body={body}";

    /**
     * Base path for requests that get post by id, title and body
     */
    private final static String BASE_PATH_POST_BY_ID_AND_TITLE_AND_BODY = "/posts?id={id}&title={title}&body={body}";

    /**
     * Base path for requests that get post by userId, title and body
     */
    private final static String BASE_PATH_POST_BY_USER_ID_AND_TITLE_AND_BODY = "/posts?userId={userId}&title={title}&body={body}";

    /**
     * Base path for requests that get post by all fields
     */
    private final static String BASE_PATH_POST_BY_ALL_FIELDS = "/posts?id={id}&userId={userId}&title={title}&body={body}";

    /**
     * Request specification builder
     */
    private final static RequestSpecificationBuilder requestSpecificationBuilder = new RequestSpecificationBuilder();

    /**
     * Response specification builder
     */
    private final static ResponseSpecificationBuilder responseSpecificationBuilder = new ResponseSpecificationBuilder();

    /**
     * Specification for requests which get posts by id
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ID
    );

    /**
     * Specification for requests which get all posts
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_ALL
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_ALL_POSTS
    );

    /**
     * Specification for requests which get posts by userId
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POSTS_BY_USER_ID
    );

    /**
     * Specification for requests which get posts by title
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_TITLE
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POSTS_BY_TITLE
    );

    /**
     * Specification for requests which get posts by body (field)
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_BODY
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POSTS_BY_BODY
    );

    /**
     * Specification for requests which get post by id and userId
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_USER_ID
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ID_AND_USER_ID
    );

    /**
     * Specification for requests which get post by id and title
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_TITLE
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ID_AND_TITLE
    );

    /**
     * Specification for requests which get post by id and body
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_BODY
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ID_AND_BODY
    );

    /**
     * Specification for requests which get posts by userId and title
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID_AND_TITLE
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POSTS_BY_USER_ID_AND_TITLE
    );

    /**
     * Specification for requests which get posts by userId and body
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID_AND_BODY
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POSTS_BY_USER_ID_AND_BODY
    );

    /**
     * Specification for requests which get posts by title and body
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_TITLE_AND_BODY
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POSTS_BY_TITLE_AND_BODY
    );

    /**
     * Specification for requests which get post by id, userId and title
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_USER_ID_AND_TITLE
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ID_AND_USER_ID_AND_TITLE
    );

    /**
     * Specification for requests which get post by id, userId and body
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_USER_ID_AND_BODY
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ID_AND_USER_ID_AND_BODY
    );

    /**
     * Specification for requests which get post by userId, title and body
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID_AND_TITLE_AND_BODY
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_USER_ID_AND_TITLE_AND_BODY
    );

    /**
     * Specification for requests which get post by id, title and body
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_TITLE_AND_BODY
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ID_AND_TITLE_AND_BODY
    );

    /**
     * Specification for requests which get post by all fields
     */
    private final static RequestSpecification REQUEST_SPECIFICATION_FOR_GET_BY_ALL_FIELDS
            = requestSpecificationBuilder.build(
            BASE_URI,
            CONTENT_TYPE,
            BASE_PATH_POST_BY_ALL_FIELDS
    );

    /**
     * Specification for responses from requests which get posts by valid id
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(ID_FIELD_NAME, anyOf(equalTo(VALID_ID), nullValue())),
            anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_ONE_POST), is(EMPTY_JSON)),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get posts by invalid id
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_INVALID_ID
            = responseSpecificationBuilder.buildSpecWithBodyCheck(
            is(EMPTY_JSON),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get all posts
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_ALL
            = responseSpecificationBuilder.buildSpecWithBodyCheck(
            anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY)),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get posts by valid userId
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue())),
            anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY)),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get posts by any invalid fields
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID
            = responseSpecificationBuilder.buildSpecWithBodyCheck(
            is(EMPTY_ARRAY),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get posts by valid title
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_TITLE
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue())),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get posts by valid body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_BODY
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid id and userId
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_USER_ID
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_ID)), nullValue()),
                    USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid id and title
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_TITLE
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_ID)), nullValue()),
                    TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid id and body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_BODY
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_ID)), nullValue()),
                    BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid userId and title
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID_AND_TITLE
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue()),
                    TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid userId and body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID_AND_BODY
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue()),
                    BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid title and body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_TITLE_AND_BODY
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue()),
                    BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid id, userId and title
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_TITLE
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_ID)), nullValue()),
                    USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue()),
                    TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid id, userId and body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_BODY
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_ID)), nullValue()),
                    USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue()),
                    BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid userId, title and body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID_AND_TITLE_AND_BODY
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue()),
                    TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue()),
                    BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by valid id, title and body
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_TITLE_AND_BODY
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_ID)), nullValue()),
                    TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue()),
                    BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Specification for responses from requests which get post by all fields
     */
    private final static ResponseSpecification RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ALL_FIELDS
            = responseSpecificationBuilder.buildSpecWithFieldsAndBodyCheck(
            Map.of(
                    ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_ID)), nullValue()),
                    USER_ID_FIELD_NAME, anyOf(everyItem(equalTo(VALID_USER_ID)), nullValue()),
                    TITLE_FIELD_NAME, anyOf(everyItem(equalTo(VALID_TITLE)), nullValue()),
                    BODY_FIELD_NAME, anyOf(everyItem(equalTo(VALID_BODY)), nullValue())
            ),
            is(anyOf(matchesJsonSchemaInClasspath(PATH_TO_SCHEMA_FOR_MANY_POSTS), is(EMPTY_ARRAY))),
            VALID_STATUS
    );

    /**
     * Params for requests which get posts by id (valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID
            = Map.of(ID_FIELD_NAME, VALID_ID);

    /**
     * Params for requests which get posts by id (invalid number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_ID_NUMBER
            = Map.of(ID_FIELD_NAME, INVALID_ID_NUMBER);

    /**
     * Params for requests which get posts by id (invalid not number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_ID_NOT_NUMBER
            = Map.of(ID_FIELD_NAME, INVALID_ID_NOT_NUMBER);

    /**
     * Params for requests which get posts by userId (valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_USER_ID
            = Map.of(USER_ID_FIELD_NAME, VALID_USER_ID);

    /**
     * Params for requests which get posts by userId (invalid number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_USER_ID_NUMBER
            = Map.of(USER_ID_FIELD_NAME, INVALID_USER_ID_NUMBER);

    /**
     * Params for requests which get posts by userId (invalid not number value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_USER_ID_NOT_NUMBER
            = Map.of(USER_ID_FIELD_NAME, INVALID_USER_ID_NOT_NUMBER);

    /**
     * Params for requests which get posts by title (valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_TITLE
            = Map.of(TITLE_FIELD_NAME, VALID_TITLE);

    /**
     * Params for requests which get posts by title (invalid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_TITLE
            = Map.of(TITLE_FIELD_NAME, INVALID_TITLE);

    /**
     * Params for requests which get posts by body (field; valid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_BODY
            = Map.of(BODY_FIELD_NAME, VALID_BODY);

    /**
     * Params for requests which get posts by body (field; invalid value)
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_INVALID_BODY
            = Map.of(BODY_FIELD_NAME, INVALID_BODY);

    /**
     * Params for requests which get posts by valid id and userId
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID_AND_USER_ID
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            USER_ID_FIELD_NAME, VALID_USER_ID
    );

    /**
     * Params for requests which get posts by valid id and title
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID_AND_TITLE
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            TITLE_FIELD_NAME, VALID_TITLE
    );

    /**
     * Params for requests which get posts by valid id and body
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID_AND_BODY
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by valid userId and title
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_USER_ID_AND_TITLE
            = Map.of(
            USER_ID_FIELD_NAME, VALID_USER_ID,
            TITLE_FIELD_NAME, VALID_TITLE
    );

    /**
     * Params for requests which get posts by valid userId and body
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_USER_ID_AND_BODY
            = Map.of(
            USER_ID_FIELD_NAME, VALID_USER_ID,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by valid title and body
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_TITLE_AND_BODY
            = Map.of(
            TITLE_FIELD_NAME, VALID_TITLE,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by valid id, userId and title
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_TITLE
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            USER_ID_FIELD_NAME, VALID_USER_ID,
            TITLE_FIELD_NAME, VALID_TITLE
    );

    /**
     * Params for requests which get posts by valid id, userId and body
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_BODY
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            USER_ID_FIELD_NAME, VALID_USER_ID,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by valid userId, title and body
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_USER_ID_AND_TITLE_AND_BODY
            = Map.of(
            USER_ID_FIELD_NAME, VALID_USER_ID,
            TITLE_FIELD_NAME, VALID_TITLE,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by valid id, title and body
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ID_AND_TITLE_AND_BODY
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            TITLE_FIELD_NAME, VALID_TITLE,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by valid all fields
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_VALID_ALL_FIELDS
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            USER_ID_FIELD_NAME, VALID_USER_ID,
            TITLE_FIELD_NAME, VALID_TITLE,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by all fields but id is invalid
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_ALL_FIELDS_INVALID_ID
            = Map.of(
            ID_FIELD_NAME, INVALID_ID_NUMBER,
            USER_ID_FIELD_NAME, VALID_USER_ID,
            TITLE_FIELD_NAME, VALID_TITLE,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by all fields but userId and title is invalid
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_ALL_FIELDS_INVALID_USER_ID_AND_TITLE
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            USER_ID_FIELD_NAME, INVALID_USER_ID_NOT_NUMBER,
            TITLE_FIELD_NAME, INVALID_TITLE,
            BODY_FIELD_NAME, VALID_BODY
    );

    /**
     * Params for requests which get posts by all fields but valid only id
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_ALL_FIELDS_VALID_ID_ONLY
            = Map.of(
            ID_FIELD_NAME, VALID_ID,
            USER_ID_FIELD_NAME, INVALID_USER_ID_NOT_NUMBER,
            TITLE_FIELD_NAME, INVALID_TITLE,
            BODY_FIELD_NAME, INVALID_BODY
    );

    /**
     * Params for requests which get posts by all invalid fields
     */
    private final static Map<String, Object> PARAMS_FOR_GET_BY_ALL_INVALID_FIELDS
            = Map.of(
            ID_FIELD_NAME, INVALID_ID_NUMBER,
            USER_ID_FIELD_NAME, INVALID_USER_ID_NOT_NUMBER,
            TITLE_FIELD_NAME, INVALID_TITLE,
            BODY_FIELD_NAME, INVALID_BODY
    );

    /**
     * Builder for tests
     */
    private final TestAPIBuilder testAPIBuilder = new TestAPIBuilder();

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    /**
     * The next 3 tests is for test case: get a resource by id
     */
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

    /**
     * This test is for test case: Get list of all resources
     */
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

    /**
     * The next tests is for test case: Filtering by query parameters (like /posts?userId=1)
     */
    @Test
    public void testGetPostsByInvalidUserIdWhichIsNumber() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID,
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
                PARAMS_FOR_GET_BY_INVALID_USER_ID_NUMBER
        );
    }

    @Test
    public void testGetPostsByInvalidUserIdWhichIsNotNumber() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID,
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
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
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
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
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
                PARAMS_FOR_GET_BY_INVALID_BODY
        );
    }

    @Test
    public void testGetPostByValidIdAndUserId() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_USER_ID,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_USER_ID,
                PARAMS_FOR_GET_BY_VALID_ID_AND_USER_ID
        );
    }

    @Test
    public void testGetPostByValidIdAndTitle() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_TITLE,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_TITLE,
                PARAMS_FOR_GET_BY_VALID_ID_AND_TITLE
        );
    }

    @Test
    public void testGetPostByValidIdAndBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_BODY,
                PARAMS_FOR_GET_BY_VALID_ID_AND_BODY
        );
    }

    @Test
    public void testGetPostByValidUserIdAndTitle() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID_AND_TITLE,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID_AND_TITLE,
                PARAMS_FOR_GET_BY_VALID_USER_ID_AND_TITLE
        );
    }

    @Test
    public void testGetPostByValidUserIdAndBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID_AND_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID_AND_BODY,
                PARAMS_FOR_GET_BY_VALID_USER_ID_AND_BODY
        );
    }

    @Test
    public void testGetPostByValidTitleAndBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_TITLE_AND_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_TITLE_AND_BODY,
                PARAMS_FOR_GET_BY_VALID_TITLE_AND_BODY
        );
    }

    @Test
    public void testGetPostByValidIdAndUserIdAndTitle() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_USER_ID_AND_TITLE,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_TITLE,
                PARAMS_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_TITLE
        );
    }

    @Test
    public void testGetPostByValidIdAndUserIdAndBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_USER_ID_AND_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_BODY,
                PARAMS_FOR_GET_BY_VALID_ID_AND_USER_ID_AND_BODY
        );
    }

    @Test
    public void testGetPostByValidUserIdAndTitleAndBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_USER_ID_AND_TITLE_AND_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_USER_ID_AND_TITLE_AND_BODY,
                PARAMS_FOR_GET_BY_VALID_USER_ID_AND_TITLE_AND_BODY
        );
    }

    @Test
    public void testGetPostByValidIdAndTitleAndBody() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ID_AND_TITLE_AND_BODY,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ID_AND_TITLE_AND_BODY,
                PARAMS_FOR_GET_BY_VALID_ID_AND_TITLE_AND_BODY
        );
    }

    @Test
    public void testGetPostByValidAllFields() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ALL_FIELDS,
                RESPONSE_SPECIFICATION_FOR_GET_BY_VALID_ALL_FIELDS,
                PARAMS_FOR_GET_BY_VALID_ALL_FIELDS
        );
    }

    @Test
    public void testGetPostByAllFieldsOneInvalidField() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ALL_FIELDS,
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
                PARAMS_FOR_GET_BY_ALL_FIELDS_INVALID_ID
        );
    }

    @Test
    public void testGetPostByAllFieldsTwoInvalidFields() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ALL_FIELDS,
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
                PARAMS_FOR_GET_BY_ALL_FIELDS_INVALID_USER_ID_AND_TITLE
        );
    }

    @Test
    public void testGetPostByAllFieldsThreeInvalidFields() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ALL_FIELDS,
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
                PARAMS_FOR_GET_BY_ALL_FIELDS_VALID_ID_ONLY
        );
    }

    @Test
    public void testGetPostByAllInvalidFields() {
        testAPIBuilder.buildTestWithParams(
                REQUEST_SPECIFICATION_FOR_GET_BY_ALL_FIELDS,
                RESPONSE_SPECIFICATION_FOR_GET_POSTS_WHEN_ANY_FIELD_IS_INVALID,
                PARAMS_FOR_GET_BY_ALL_INVALID_FIELDS
        );
    }

}
