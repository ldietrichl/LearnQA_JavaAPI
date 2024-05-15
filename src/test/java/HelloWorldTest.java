import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldTest {


    @Test
    public void testHelloWorld(){
        //System.out.println("Hello from Dietrich");

        Response response = RestAssured
                .given()
                .queryParam("name","Dietrich")
                .get("https://playground.learnqa.ru/api/get_text")
                .andReturn();
        response.prettyPrint();

    }

    @Test
    public void testHelloWorld2(){
        //System.out.println("Hello from Dietrich");
        Map<String, String> params = new HashMap<>();
        params.put("name", "Dietrich");

        Response response2 = RestAssured
                .given()
                .queryParams(params)
                .get( "https://playground.learnqa.ru/api/hello")
                .andReturn();
        response2.prettyPrint();

    }

    @Test
    public void testHelloWorld3() {
        //System.out.println("Hello from Dietrich");
        Map<String, String> params2 = new HashMap<>();
        params2.put("name", "Dietrich");

        JsonPath response3 = RestAssured
                .given()
                .queryParams(params2)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String name = response3.get("answer");
        if (name == null) {
            System.out.println("The key 'answer2' is absent");
        } else {
            System.out.println(name);
        }
    }



    @Test
    public void TestEx06(){
        Map<String, Object> body = new HashMap<>();
        body.put("param1","value1");
        body.put("param2","value2");

        Response response = RestAssured
                .given()
                //.queryParam("param1","value1")
                //.queryParam("param2","value2")
                //.body("param1=value1&param2=value2")
                //.body("{\"param1\":\"value1\",\"param2\":\"value2\"}")
                .body(body)
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();
        response.print();
    }

    @Test
    public void TestEx07(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                //.get("https://playground.learnqa.ru/api/someshit") //404
                //.get("https://playground.learnqa.ru/api/get_500") //500
                .get("https://playground.learnqa.ru/api/get_303") //303
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Test
    public void TestEx08(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1","myValue1");
        headers.put("myHeader2","myValue2");

        Response response = RestAssured
                .given()
                //.headers(headers)
                .redirects()
                .follow(false)
                .when()
                //.get("https://playground.learnqa.ru/api/show_all_headers")
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();


        //Headers responseHeaders = response.getHeaders();
        String locatiuonHeader  = response.getHeader("Location");
        System.out.println(locatiuonHeader);
    }

    @Test
    public void TestEx09_1(){
        Map<String, String> data = new HashMap<>();
        data.put("login","secret_login2");
        data.put("password","secret_pass2");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                //.get("https://playground.learnqa.ru/api/show_all_headers")
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();


        System.out.println("\nPretty text:");
        response.prettyPrint();

        System.out.println("\nHeaders:");
        Headers responceHeaders = response.getHeaders() ;
        System.out.println(responceHeaders);

        System.out.println("\nCookies:");
        Map<String, String> responceCookies = response.getCookies();
        System.out.println(responceCookies);


        String responceCookies2 = response.getCookie("auth_cookie");
        System.out.println(responceCookies2);


    }

    @Test
    public void TestEx09_2(){
        Map<String, String> data = new HashMap<>();
        data.put("login","secret_login2");
        data.put("password","secret_pass2");

        Response responseForGet = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        String responceCookie = responseForGet.getCookie("auth_cookie");

        Map<String, String> cookies = new HashMap<>();
        if(responceCookie != null){
            cookies.put("auth_cookie",responceCookie);
        }




    }
}