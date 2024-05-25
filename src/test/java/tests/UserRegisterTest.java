package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {
    String email = "vinkotov@example.com";

    @Test
    public void TestCreateUserWithExistingEmail(){
        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password","123");
        userData.put("username","learnqa");
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals( responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '"+ email + "' already exists");




    }


    @Test
    public void TestCreateUserSuccessfully(){
        String email = DataGenerator.getRandomEmail();
        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData.put("password","123");
        userData.put("username","learnqa");
        userData.put("firstName","learnqa");
        userData.put("lastName","learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals( responseCreateAuth,200);
        Assertions.assertJsonHasField( responseCreateAuth,"id");
       // Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '"+ email + "' already exists");
        System.out.println(responseCreateAuth.asString());




    }
}
