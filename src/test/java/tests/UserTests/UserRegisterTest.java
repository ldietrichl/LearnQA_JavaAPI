package tests.UserTests;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
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

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

@Epic("Registration cases")
@Feature("User Registration")
public class UserRegisterTest extends BaseTestCase {
    String email = "vinkotov@example.com";
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Story("Register existing user")
    @Description("This test tries to create a user with an email that already exists in the system")
    @DisplayName("Test Create User With Existing Email")
    @Severity(SeverityLevel.CRITICAL)
    public void TestCreateUserWithExistingEmail(){
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api_dev/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Story("Invalid registration with too short field")
    @Description("This test attempts to register a user with fields that are too short")
    @DisplayName("Test Create User With Too Short Field")
    @Severity(SeverityLevel.NORMAL)
    public void TestCreateUserWithShortField(String missingField){
        Map<String, String> userData = DataGenerator.getRegistrationData();
        userData.put(missingField, "a");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api_dev/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of '" + missingField + "' field is too short");
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Story("Invalid registration with too large field")
    @Description("This test attempts to register a user with fields that are too long")
    @DisplayName("Test Create User With Too Large Field")
    @Severity(SeverityLevel.NORMAL)
    public void TestCreateUserWithLargeField(String missingField){
        Map<String, String> userData = DataGenerator.getRegistrationData();
        userData.put(missingField, DataGenerator.generateRandomString(255));

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api_dev/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of '" + missingField + "' field is too long");
    }

    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Story("Invalid registration with missing field")
    @Description("This test attempts to register a user with missing fields")
    @DisplayName("Test Create User With Missing Field")
    @Severity(SeverityLevel.NORMAL)
    public void TestCreateUserWithMissingField(String missingField){
        Map<String, String> userData = DataGenerator.getRegistrationData();
        userData.remove(missingField);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api_dev/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The following required params are missed: " + missingField);
    }

    @Test
    @Story("Successful user registration")
    @Description("This test successfully creates a user")
    @DisplayName("Test Create User Successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void TestCreateUserSuccessfully(){
        String email = DataGenerator.getRandomEmail();
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = given()
                .body(userData)
                .post("https://playground.learnqa.ru/api_dev/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    @Test
    @Story("Invalid registration with invalid email")
    @Description("This test attempts to register a user with an invalid email format")
    @DisplayName("Test Create User With Invalid Email")
    @Severity(SeverityLevel.NORMAL)
    public void TestCreateUserInvalidEmail(){
        String email = DataGenerator.getInvalidEmail();
        Map<String, String> userData = DataGenerator.getRegistrationData();
        userData.put("email", email);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequestInvalidEmail(
                        "https://playground.learnqa.ru/api_dev/user/",
                        userData
                );

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }
}
