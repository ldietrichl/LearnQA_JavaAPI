package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import lib.ApiCoreRequests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserRegisterTest extends BaseTestCase {
    String email = "vinkotov@example.com";
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void TestCreateUserWithExistingEmail(){
        Map<String, String> userData = new HashMap<>();
        userData.put("email",email);
        userData = DataGenerator.getRegistrationData(userData);


        Response responseCreateAuth = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals( responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '"+ email + "' already exists");


    }



    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Description("This test invalid registration user by too short field")
    @DisplayName("Test negative test by too short field")
    public void TestCreateUserWithShortField(String missingField){
        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData(userData);
        userData.put(missingField,"a");
        //System.out.println(userData);


        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals( responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"The value of '"+missingField+"' field is too short");


    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Description("This test invalid registration user by too large field")
    @DisplayName("Test negative test by too large field")
    public void TestCreateUserWithLargeField(String missingField){
        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData(userData);
        userData.put(missingField,DataGenerator.generateRandomString(255));
        //System.out.println(userData);


        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals( responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"The value of '"+missingField+"' field is too long");


    }



    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Description("This test invalid registration user by missing fields")
    @DisplayName("Test negative test by missing field")
    public void TestCreateUserWithMissingField(String missingField){
        Map<String, String> userData = new HashMap<>();
        userData = DataGenerator.getRegistrationData(userData);
        userData.remove(missingField);
        //System.out.println(userData);


        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals( responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"The following required params are missed: "+missingField);


    }





    @Test
    public void TestCreateUserSuccessfully(){
        String email = DataGenerator.getRandomEmail();
        Map<String, String> userData =DataGenerator.getRegistrationData();


        Response responseCreateAuth = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals( responseCreateAuth,200);
        Assertions.assertJsonHasField( responseCreateAuth,"id");
       // Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '"+ email + "' already exists");
       // System.out.println(responseCreateAuth.asString());

    }

    @Test
    @Description("This test invalid registration user by email")
    @DisplayName("Test negative invalid email format")
    public void TestCreateUserInvalidEmail(){
        String email = DataGenerator.getInvalidEmail();
        Map<String, String> userData =DataGenerator.getRegistrationData();
        userData.put("email",email);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals( responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth,"Invalid email format");

    }



}
