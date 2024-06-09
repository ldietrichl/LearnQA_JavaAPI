package tests.UserTests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Get-test cases")
@Feature("Homework")
public class UserGetTestHomework extends BaseTestCase {

    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void loginUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api_dev/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");


        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");
    }


        @Description("This test checks authorization status w/o cookie or token")
        @DisplayName("Test negative auth user")
        @Test
        public void testNegativeUser(){

        Response responseForCheck = apiCoreRequests.makeGetRequest(
                "https://playground.learnqa.ru/api_dev/user/1",
                this.cookie,
                this.header
        );

        Assertions.assertJsonHasField(responseForCheck,"username");
        Assertions.asserJsonNotField(responseForCheck,"firstName");
        Assertions.asserJsonNotField(responseForCheck,"password");
        Assertions.asserJsonNotField(responseForCheck,"lastName");
        Assertions.asserJsonNotField(responseForCheck,"email");

    }
}
