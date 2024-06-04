package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {

    @Step("Make a GET-requests with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();

    }

    @Step("Make a PUT-requests with token and auth cookie")
    public Response makePutEditRequest(String url, String token, String cookie, Map<String, String> EditData){
        return given()
                .filter(new AllureRestAssured())
                .body(EditData)
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .put(url)
                .andReturn();

    }

    @Step("Make a PUT-requests w/o token and auth cookie")
    public Response makePutEditRequestNoAuth(String url,  Map<String, String> EditData){
        return given()
                .filter(new AllureRestAssured())
                .body(EditData)
                .put(url)
                .andReturn();

    }

    @Step("Make a GET-requests with auth cookie only")
    public Response makeGetRequestWithCookie(String url,  String cookie){
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();

    }

    @Step("Make a GET-requests with token only")
    public Response makeGetRequestWithToken(String url, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();

    }

    @Step("Make a POST-requests with token only")
    public Response makePostRequest(String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();

    }
    @Step("Make a POST-requests with token only")
    public Response makeDeleteRequest(String url, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .delete(url)
                .andReturn();

    }


    @Step("Make a POST-requests with token only")
    public Response makeGetRequestWithAuth(String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .get(url)
                .andReturn();

    }

    @Step("Make a POST-request for registration")
    public Response makePostRequestInvalidEmail(String url, Map<String, String> userData){
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();

    }







}
