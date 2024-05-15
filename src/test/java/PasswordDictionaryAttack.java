/*import com.opencsv.CSVReader;
import io.restassured.RestAssured;
import io.restassured.internal.support.FileReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import com.opencsv.CSVReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.FileReader;
import java.io.IOException;


public class PasswordDictionaryAttack {

        @Test
    public void PasswordDictionaryAttack() {

        Map<String, String> data = new HashMap<>();
        data.put("login","secret_login2");
        data.put("password","secret_pass2");

        Response responseForGet = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                .andReturn();

        String responceCookie = responseForGet.getCookie("auth_cookie");


        Map<String, String> cookies = new HashMap<>();
        if(responceCookie != null){
            cookies.put("auth_cookie",responceCookie);
        }

        System.out.println(responceCookie);

        Response responseForCheck = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();


        responseForCheck.print();

    }
}


 */