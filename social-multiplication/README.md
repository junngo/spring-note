# Social Multiplication

This project topic is the Microservices. Start to practice for Microservices step by step.

---

## Topic

- TDD

---

## Learning Log

- I can learn difference between unit test and integration test and also TDD. This [code](https://github.com/junngo/spring-note/commit/02acd2d591dabb44d1d3f76d605b32c438ebea6f) have to test by integration test(`@SpringBootTest`). I made the RandomGeneratorService that is interface without impl. This code can not run because there is not impl(`RandomGeneratorService Bean`). But I can pass the test by using `@SpringBootTest` and mock without impl. If I only do the mock test, It happens the exception. I can make RandomGeneratorService by mock and inject it with `@SpringBootTest`.
- When I write the code, I have to consider the unit test. The good method is to do TDD. If you make the test code before you write the business code, This order makes you consider to wirte the business code for unit test.
