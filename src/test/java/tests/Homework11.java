package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import org.junit.jupiter.api.Test;

public class Homework11 {

    @Test
    public void FirstMessage() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        String locatiuonCokies  = response.getCookies().toString();
        System.out.println(locatiuonCokies);
        Assertions.assertCookieHw(response,"HomeWork","hw_value");
    }
}
