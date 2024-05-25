package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import org.junit.jupiter.api.Test;

public class Homework13 {

    @Test
    public void FirstMessage() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        String headers = response.getHeaders().toString();
        System.out.println(headers);
        Assertions.assertHeaderHw(response,"x-secret-homework-header","Some secret value");
    }
}
