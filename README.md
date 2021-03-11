# qaa-test
[![Language](http://img.shields.io/badge/language-java-brightgreen.svg)](https://www.java.com/)
[![License](http://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/samtools/PolinaBevad/bio_relatives)

QAA home work 

## Table of Contents
-   [Used technologies](#used-technologies)
-   [Tasks](#tasks)
    -   [The first task](#the-first-task)
    -   [The second task](#the-second-task)
    -   [The third task](#the-third-task)
-   [Maintainer](#maintainer)
-   [License](#license)

## Used technologies
-   Java 11
-   JUnit 5
-   Allure
-   RestAssured
-   Hamcrest
-   Maven

## Tasks
### The first task
In this task, it was necessary to test the API https://jsonplaceholder.typicode.com/posts. 

Test cases:
- Filtering by query parameters (like /posts?userId=1)
- Get a resource by id
- Get list of all resources

26 tests were developed:
- 1 test for getting all posts
- 3 tests for getting post by id: valid id (1), non-existent id (-1), invalid id (b)
- 22 tests for getting posts by parameters:
    - 3 tests for getting posts by userId: valid userId (1), non-existent userId (-1), invalid userId (b)
    - 2 tests for getting posts by title: valid title, invalid title (not string)
    - 2 tests for getting posts by body: valid body, invalid body (not string)
    - 11 tests for getting posts by multiple valid parameters:
        - 1 test for getting posts by id and userId
        - 1 test for getting posts by id and title 
        - 1 test for getting posts by id and body
        - 1 test for getting posts by userId and title
        - 1 test for getting posts by userId and body
        - 1 test for getting posts by title and body
        - 1 test for getting posts by id, userId and title
        - 1 test for getting posts by id, userId and body
        - 1 test for getting posts by userId, title and body
        - 1 test for getting posts by id, title and body
        - 1 test for getting post by all fields
    - 4 tests for getting posts by multiple parameters but any parameters are invalid:
        - 1 test for getting post by all fields (id is invalid)
        - 1 test for getting post by all fields (userId and title are invalid)
        - 1 test for getting post by all fields (userId, title and body are invalid)
        - 1 test for getting post by all invalid fields

All tests were developed in directory `src/test/java/ru/marchenko/qaa/test` in class `PostsAPIFunctionalTest`.

Tests were runned with Maven:
```
- mvn clean test
- mvn allure:serve
```

After executing the commands, an Allure report appeared in browser:

![image](https://user-images.githubusercontent.com/44652081/110792511-8f83b700-8284-11eb-96ef-3a43da0a06f0.png)

As we can see, there are 2 failed tests. Lets look at them:

![image](https://user-images.githubusercontent.com/44652081/110792792-e12c4180-8284-11eb-8fb8-1bc76d0ebe4a.png)

These tests are for request `GET /posts/{id}` with non-existent and invalid id value. As described in third task, this request should return status OK (200), but it returned status Not Found (404). As I understood from tasks, this is bug (so code checks status 200 in each test), but in my opinion we need to use status 404, when similar situations (getting resource by non-existent id) occure.   

### The second task

In this task it was necessary to describe test cases about CREATE, UPDATE and DELETE operations.

| Summary | Steps | Expected result |
|-------------------------------------------------------|------------------------------------------------|----------------------------------------------------------|
|Create post with valid fields.|Send POST request with uri `/posts` and body with valid data: existant userId, valid title and body.|Status CREATED (201) and non-empty JSON with properties with the same values as in the request body, but property "id" should be not null.|
|Create post with non-existant "userId" field.|Send POST request with uri `/posts` and body with this data: non-existant userId (for example, -1), valid title and body.|Status BAD REQUEST (400).|
|Create post with invalid "userId" field.|Send POST request with uri `/posts` and body with this data: invalid userId (for example, "id"), valid title and body.|Status BAD REQUEST (400).|
|Create post with invalid "title" field.|Send POST request with uri `/posts` and body with this data: valid userId and body, invalid title (for example, 5 as number).|Status BAD REQUEST (400).|
|Create post with invalid "body" field.|Send POST request with uri `/posts` and body with this data: valid userId and title, invalid body (for example, 5 as number).|Status BAD REQUEST (400).|
|Update post by valid and existant id with correct updatable info.|Send PUT request with uri `/posts/{id}` (id is valid and existant) and body with this data: valid userId, title and body.|Status OK (200) and non-empty JSON with properties with the same values as in the request body (id as in uri `/posts/{id}`).|
|Update post by non-existant id with correct updatable info.|Send PUT request with uri `/posts/{id}` (id is non-existant, for example, -1) and body with this data: valid userId, title and body.|Status NOT FOUND (404).|
|Update post by invalid id with correct updatable info.|Send PUT request with uri `/posts/{id}` (id is invalid, for example, "b") and body with this data: valid userId, title and body.|Status BAD REQUEST (400).|
|Update post by valid id with incorrect updatable info.|Send PUT request with uri `/posts/{id}` (id is valid and existant) and body with this data: invalid userId, title and body.|Status BAD REQUEST (400).|
    
### The third task

In this task it was necessary to create a bug report for the next issue:
```
The request GET /posts/0 returned 404 Not Found, but you expect an empty list with 200 OK
```
Bug report:
-   Bug id: 1
-   Bug name: Incorect behaviour of request GET /posts/0.
-   Build number: 1.0.0
-   Severity: Major
-   Priority: High
-   Reported by: Marchenko Vladislav
-   Reported on: 11.03.2021
-   Reason: Defect
-   Status: Open
-   Description: The request GET /posts/0 returned 404 Not Found, but you expect an empty list with 200 OK.
-   Steps to reproduce: send request GET /posts/0 to our API.
-   Expected result: Empty list and status 200 OK.
-   Actual result: status 404 Not Found.
-   Attachment:
![image](https://user-images.githubusercontent.com/44652081/110858799-908c0700-82cb-11eb-8ebe-12a67eb7d33f.png)

## Maintainer
[Vladislav Marchenko](https://github.com/MarchenkoVladislav)

## License
This project is licenced under the terms of the [MIT](LICENSE) license.
