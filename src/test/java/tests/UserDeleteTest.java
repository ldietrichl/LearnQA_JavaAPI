package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

public class UserDeleteTest extends BaseTestCase {
    String userId;
    Map<String,String> userData;
    String cookie;
    String header;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test try delete test user Vinkotov, must be impossible")
    @DisplayName("Test negative delete test user")
    public void deleteTestUser() {
        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        System.out.println(authData);

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",
                        authData
                );
        this.userId = responseGetAuth.jsonPath().getString("user_id");

        Assertions.assertResponseCodeEquals( responseGetAuth,200);
        Assertions.assertJsonBodyValue(responseGetAuth,"user_id","2");
        //responseGetAuth.prettyPrint();
        //System.out.println(this.userId);

        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");

        //TRY TO DEL makePostRequest

        Response responseDelUser = apiCoreRequests
                .makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/"+this.userId,
                        this.header,
                        this.cookie
                );

        Assertions.assertResponseCodeEquals( responseDelUser,400);
        Assertions.assertJsonBodyValue(responseDelUser,"error","Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
        //responseDelUser.prettyPrint();

        //CHECK THAT USER STILL EXIST makeGetRequest

        Response responseForCheck = apiCoreRequests
                .makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"+this.userId,
                        this.header,
                        this.cookie
                );

        //responseForCheck.prettyPrint();
        String[]  expectedFields= {"username","firstName","lastName","email"};
        Assertions.assertJsonHasFields(responseForCheck,expectedFields);
        Assertions.assertResponseCodeEquals( responseDelUser,400); //why RC=400?

    }



    @Test
    @Description("This test  generate and delete user")
    @DisplayName("Test positive delete user")
    public void deleteGeneratedUser() {
        //GENERATE USER
        this.userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",
                        userData
                );

        //responseCreateAuth.prettyPrint();

        //DO LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",
                        authData
                );
        this.userId = responseGetAuth.jsonPath().getString("user_id");
        //System.out.println(userId);

        //responseGetAuth.prettyPrint();
        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");

        //CHECK THAT USER EXIST

        Response responseForCheck = apiCoreRequests
                .makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"+this.userId,
                        this.header,
                        this.cookie
                );
        //responseForCheck.prettyPrint();
        String[]  expectedFields= {"username","firstName","lastName","email"};
        Assertions.assertJsonHasFields(responseForCheck,expectedFields);

        //TRY TO DEL

        Response responseDelUser = apiCoreRequests
                .makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/"+this.userId,
                        this.header,
                        this.cookie
                );
        //responseDelUser.prettyPrint();

        Assertions.assertResponseCodeEquals( responseDelUser,200);
        Assertions.assertJsonBodyValue(responseDelUser,"success","!");
        //responseDelUser.prettyPrint();

    }

    @Test
    @Description("This test try delete stranger user w/o AuthData")
    @DisplayName("Test negative delete stranger")
    public void testDelStranger() {

        //GENERATE USER
        this.userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",
                        userData
                );

        //responseCreateAuth.prettyPrint();

        //DO LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",
                        authData
                );

        //System.out.println(userId);
        int strangerId=responseGetAuth.jsonPath().getInt("user_id")-100;
        //System.out.println(strangerId);

        //responseGetAuth.prettyPrint();
        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");

        //CHECK THAT USER EXIST

        Response responseForCheck = apiCoreRequests
                .makeGetRequest(
                        "https://playground.learnqa.ru/api/user/"+strangerId,
                        this.header,
                        this.cookie
                );
        //responseForCheck.prettyPrint();
        Assertions.assertJsonHasField(responseForCheck,"username");

        //TRY TO DEL Stranger ACC


        Response responseDelUser = apiCoreRequests
                .makeDeleteRequest(
                        "https://playground.learnqa.ru/api/user/"+strangerId,
                        this.header,
                        this.cookie
                );
        //responseDelUser.prettyPrint();

        Assertions.assertResponseCodeEquals( responseDelUser,400);
        Assertions.assertJsonBodyValue(responseDelUser,"error","This user can only delete their own account.");
        //responseDelUser.prettyPrint();*/
    }



    }
