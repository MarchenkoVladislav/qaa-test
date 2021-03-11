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
- 3 tests for getting post by id: valid id (1), invalid number id (-1), invalid not number id (b)
- 22 tests for getting posts by parameters:
    - 3 tests for getting posts by userId: valid userId (1), invalid number userId (-1), invalid not number userId (b)
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

![image](https://user-images.githubusercontent.com/44652081/110788921-50ebfd80-8280-11eb-908b-3880765d6f5d.png)

As we can see, there are 2 failed tests. Lets look on them:

![image](https://user-images.githubusercontent.com/44652081/110789192-a4f6e200-8280-11eb-8efe-3e0c2d145827.png)

These tests are for request GET /posts/{id} with invalid id value. 

### The second task
    
### The third task

## Maintainer
[Vladislav Marchenko](https://github.com/MarchenkoVladislav)

## License
This project is licenced under the terms of the [MIT](LICENSE) license.
