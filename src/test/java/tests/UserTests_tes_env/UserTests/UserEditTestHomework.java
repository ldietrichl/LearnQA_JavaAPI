package tests.UserTests_tes_env.UserTests;

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

public class UserEditTestHomework extends BaseTestCase {
    String userId;
    Map<String,String> userData;
    String cookie;
    String header;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void createUserAndLogin() {
        //GENERATE USER
        this.userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",
                        userData
                );

        //responseCreateAuth.prettyPrint();
        this.userId = responseCreateAuth.jsonPath().getString("id");


        //DO LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login",
                        authData
                );

        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");

    }

    @Test
    @Description("This test try change firstName w/o AuthData")
    @DisplayName("Test negative edit firstName")
    public void testEditNoAuth() {

        //EDIT noAutH
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUserNoAuth = apiCoreRequests
                .makePutEditRequestNoAuth(
                        "https://playground.learnqa.ru/api/user/" + userId,
                        editData);

        //responseEditUserNoAuth.print();
        Assertions.assertResponseCodeEquals( responseEditUserNoAuth,400);
        Assertions.assertJsonBodyValue(responseEditUserNoAuth,"error","Auth token not supplied");
    }

    @Test
    @Description("This test try change email to invalid value w AuthData")
    @DisplayName("Test negative w invalid email")
    public void testEditInvalidEmail() {
        //EDIT invalid email
        String newEmail = DataGenerator.getInvalidEmail();
        Map<String, String> editDataEmail = new HashMap<>();
        editDataEmail.put("email", newEmail);

        Response responseEditUserEmail = apiCoreRequests
                .makePutEditRequest("https://playground.learnqa.ru/api/user/" + userId,
                        this.header,
                        this.cookie,
                        editDataEmail
                );

        Assertions.assertResponseCodeEquals( responseEditUserEmail,400);
        Assertions.assertJsonBodyValue(responseEditUserEmail,"error","Invalid email format");
        //responseEditUserEmail.prettyPrint();
    }
    @Test
    @Description("This test trying change stranger email with AuthData")
    @DisplayName("Test negative change email")
    public void testEditStrangerEmail() {
        //EDIT valid email other user
        String newStrangerEmail = DataGenerator.getRandomEmail();
        Map<String, String> editDataStranger = new HashMap<>();
        editDataStranger.put("email", newStrangerEmail);

        Response responseEditDataStrangerEmail = apiCoreRequests
                .makePutEditRequest("https://playground.learnqa.ru/api/user/50",
                        this.header,
                        this.cookie,
                        editDataStranger
                );
        //responseEditDataStrangerEmail.prettyPrint();
        Assertions.assertResponseCodeEquals( responseEditDataStrangerEmail,400);
        Assertions.assertJsonBodyValue(responseEditDataStrangerEmail,"error","This user can only edit their own data.");
    }

        @Test
        @Description("This test change email to short value  w AuthData")
        @DisplayName("Test negative edit email")
        public void testEditValidEmail() {
            //EDIT short firstName
            Map<String, String> editDataName = new HashMap<>();
            editDataName.put("firstName", "a");

            Response responseEditUserName = apiCoreRequests
                    .makePutEditRequest("https://playground.learnqa.ru/api/user/" + userId,
                            this.header,
                            this.cookie,
                            editDataName
                    );

            //responseEditUserName.prettyPrint();
            Assertions.assertResponseCodeEquals( responseEditUserName,400);
            Assertions.assertJsonBodyValue(responseEditUserName,"error","The value for field `firstName` is too short");
        }


    }
